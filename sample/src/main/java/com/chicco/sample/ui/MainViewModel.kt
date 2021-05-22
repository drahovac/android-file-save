package com.chicco.sample.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chicco.filesave.usecase.FileSaveController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private companion object {
        const val SAMPLE_PDF_NAME = "Sample.pdf"
    }

    private val saveController: FileSaveController = FileSaveController(getApplication())
    val downloadPdfJob: LiveData<Boolean> = MutableLiveData(false)

    fun downloadPdf() {
        if (downloadPdfJob.value == false) {
            GlobalScope.launch {
                downloadPdfJob.asMutable().postValue(true)
                withContext(Dispatchers.IO) {
                    val inputStream: InputStream =
                        getApplication<Application>().assets.open(SAMPLE_PDF_NAME)

                    saveController.savePdfFile(inputStream, SAMPLE_PDF_NAME)
                }
                downloadPdfJob.asMutable().postValue(false)
            }
        }
    }

    private fun <T> LiveData<T>.asMutable() = this as MutableLiveData<T>
}