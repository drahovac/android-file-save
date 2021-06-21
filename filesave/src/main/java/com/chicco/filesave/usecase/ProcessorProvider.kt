package com.chicco.filesave.usecase

import android.content.Context
import android.os.Build
import com.chicco.filesave.dataaccess.*

internal data class ProcessorProvider(private val context: Context) {

    val downloadsProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            DownloadsSaveProcessor(context.contentResolver)
        } else {
            DownloadsSaveLegacyProcessor()
        }
    val imagesFileSaveProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ImageFileSaveProcessor(context.contentResolver)
        } else {
            ImageFileSaveLegacyProcessor()
        }
}