package com.chicco.filesave.usecase

import android.content.Context
import android.os.Build
import com.chicco.filesave.dataaccess.*

internal data class ProcessorProvider(
    private val context: Context,
    private val fileProviderName: String?) {

    val downloadsProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            DownloadsSaveProcessor(context.contentResolver, context)
        } else {
            DownloadsSaveLegacyProcessor(context, fileProviderName)
        }
    val imagesFileSaveProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ImageFileSaveProcessor(context.contentResolver, context)
        } else {
            ImageFileSaveLegacyProcessor(context, fileProviderName)
        }
    val bitmapSaveProcessor: BitmapSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ImageFileSaveProcessor(context.contentResolver, context)
        } else {
            ImageFileSaveLegacyProcessor(context, fileProviderName)
        }
}