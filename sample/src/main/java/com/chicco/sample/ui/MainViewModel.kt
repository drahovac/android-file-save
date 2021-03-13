package com.chicco.sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val downloadPdfJob: LiveData<Boolean> = MutableLiveData(false)

    fun downloadPdf() {
        if (downloadPdfJob.value == false) {
            downloadPdfJob.asMutable().postValue(true)

            // some actual job
        }
    }

    fun <T> LiveData<T>.asMutable() = this as MutableLiveData<T>
}