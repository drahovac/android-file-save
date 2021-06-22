package com.chicco.sample

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chicco.filesave.domain.BitmapContent
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
        const val SAMPLE_BITMAP_NAME = "Sample_bitmap.png"
    }

    private val saveController: FileSaveController =
        FileSaveController.getInstance(application())
    val downloadPendingJob: LiveData<Boolean> = MutableLiveData(false)
    val pdfSaveResult: LiveData<String> = MutableLiveData("")
    val imageSaveResult: LiveData<String> = MutableLiveData("")
    val bitmapSaveResult: LiveData<String> = MutableLiveData("")

    fun savePdf() {
        startSaveJob(pdfSaveResult.asMutable()) {
            val inputStream: InputStream =
                application().assets.open(SAMPLE_PDF_NAME)

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

    private fun startSaveJob(
        resultData: MutableLiveData<String>,
        saveFileAction: suspend () -> FileSaveResult
    ) {
        if (downloadPendingJob.value == false) {
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

    fun saveImage() {
        startSaveJob(imageSaveResult.asMutable()) {
            val inputStream: InputStream =
                application().assets.open(SAMPLE_IMAGE_NAME)

            saveController.saveImageFile(
                FileContent(
                    data = inputStream,
                    fileNameWithoutSuffix = SAMPLE_IMAGE_NAME,
                    suffix = "png",
                    mimeType = "image/png",
                    subfolderName = "test_subfolder"
                )
            )
        }
    }

    fun saveBitmap() {
        val bitmap = BitmapFactory.decodeResource(
            application().resources,
            R.drawable.sample_bitmap
        )
        startSaveJob(bitmapSaveResult.asMutable())
        {
            saveController.saveBitmap(
                BitmapContent(
                    bitmap,
                    Bitmap.CompressFormat.PNG,
                    fileNameWithoutSuffix = SAMPLE_BITMAP_NAME,
                    suffix = "png",
                    mimeType = "image/png",
                    subfolderName = "test_bitmap_subfolder"
                )
            )
        }
    }

    private fun application() = getApplication<Application>()
}