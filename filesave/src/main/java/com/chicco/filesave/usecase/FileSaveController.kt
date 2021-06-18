package com.chicco.filesave.usecase

import android.content.Context
import android.os.Build
import com.chicco.filesave.dataaccess.*
import com.chicco.filesave.domain.FileContent

class FileSaveController(
    context: Context
) {
    private val downloadsProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            DownloadsSaveProcessor(context.contentResolver)
        } else {
            DownloadsSaveLegacyProcessor()
        }
    private val imagesFileSaveProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ImageFileSaveProcessor(context.contentResolver)
        } else {
            ImageFileSaveLegacyProcessor()
        }

    suspend fun savePdfFile(content: FileContent) {
        downloadsProcessor.save(content)
    }

    suspend fun saveImageFile(content: FileContent) {
        imagesFileSaveProcessor.save(content)
    }
}