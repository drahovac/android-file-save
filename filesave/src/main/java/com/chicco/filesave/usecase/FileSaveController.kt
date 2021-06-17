package com.chicco.filesave.usecase

import android.content.Context
import android.os.Build
import com.chicco.filesave.dataaccess.DownloadsSaveLegacyProcessor
import com.chicco.filesave.dataaccess.DownloadsSaveProcessor
import com.chicco.filesave.dataaccess.FileSaveProcessor
import com.chicco.filesave.dataaccess.ImageFileSaveProcessor
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
    private val imagesFileSaveProcessor: ImageFileSaveProcessor =
        ImageFileSaveProcessor(context.contentResolver)

    suspend fun savePdfFile(content: FileContent) {
        downloadsProcessor.save(content)
    }

    suspend fun saveImageFile(content: FileContent) {
        imagesFileSaveProcessor.save(content)
    }
}