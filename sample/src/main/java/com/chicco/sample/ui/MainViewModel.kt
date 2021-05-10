package com.chicco.sample.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chicco.filesave.usecase.FileSaveController
import java.io.InputStream


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private companion object {
        const val SAMPLE_PDF_NAME = "Sample.pdf"
    }

    private val saveController: FileSaveController = FileSaveController(getApplication())
    val downloadPdfJob: LiveData<Boolean> = MutableLiveData(false)

    fun downloadPdf() {
        if (downloadPdfJob.value == false) {
            downloadPdfJob.asMutable().postValue(true)
            val inputStream: InputStream =
                getApplication<Application>().assets.open(SAMPLE_PDF_NAME)

            saveController.savePdfFile(inputStream, SAMPLE_PDF_NAME)
            downloadPdfJob.asMutable().postValue(false)
            // some actual job
        }
    }

    private fun <T> LiveData<T>.asMutable() = this as MutableLiveData<T>
}