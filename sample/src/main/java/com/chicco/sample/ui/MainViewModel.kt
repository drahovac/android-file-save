package com.chicco.sample.ui

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chicco.filesave.domain.FileContent
import com.chicco.filesave.domain.FileSaveResult
import com.chicco.filesave.usecase.FileSaveController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

@SuppressLint("MissingPermission")
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private companion object {
        const val SAMPLE_FILE_NAME = "Sample"
        const val SAMPLE_PDF_NAME = "Sample.pdf"
        const val SAMPLE_IMAGE_NAME = "Sample.png"
    }

    private val saveController: FileSaveController =
        FileSaveController.getInstance(getApplication())
    val downloadPendingJob: LiveData<Boolean> = MutableLiveData(false)
    val pdfDownloadResult: LiveData<String> = MutableLiveData("")
    val imageDownloadResult: LiveData<String> = MutableLiveData("")

    fun downloadPdf() {
        startDownloadJob(pdfDownloadResult.asMutable()) {
            val inputStream: InputStream =
                getApplication<Application>().assets.open(SAMPLE_PDF_NAME)

            saveController.saveDocumentFile(
                FileContent(
                    data = inputStream,
                    fileNameWithoutSuffix = SAMPLE_FILE_NAME,
                    suffix = "pdf",
                    mimeType = "application/pdf",
                    subfolderName = "test_subfolder"
                )
            )
        }
    }

    private fun startDownloadJob(
        resultData: MutableLiveData<String>,
        saveFileAction: suspend () -> FileSaveResult
    ) {
        if (downloadPendingJob.value == false && hasWritePermission()) {
            GlobalScope.launch {
                downloadPendingJob.asMutable().postValue(true)
                withContext(Dispatchers.IO) {
                    resultData.postValue(saveFileAction().toString())
                }
                downloadPendingJob.asMutable().postValue(false)
            }
        }
    }

    private fun <T> LiveData<T>.asMutable() = this as MutableLiveData<T>

    fun downloadImage() {
        startDownloadJob(imageDownloadResult.asMutable()) {
            val inputStream: InputStream =
                getApplication<Application>().assets.open(SAMPLE_IMAGE_NAME)

            saveController.saveImageFile(
                FileContent(
                    data = inputStream,
                    fileNameWithoutSuffix = SAMPLE_FILE_NAME,
                    suffix = "png",
                    mimeType = "image/png",
                    subfolderName = "test_subfolder"
                )
            )
        }
    }

    private fun hasWritePermission(): Boolean {
        return checkSelfPermission(
            getApplication(),
            WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    }
}