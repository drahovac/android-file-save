package com.chicco.filesave.usecase

import android.content.Context
import android.os.Build
import com.chicco.filesave.dataaccess.DownloadsSaveLegacyProcessor
import com.chicco.filesave.dataaccess.DownloadsSaveProcessor
import com.chicco.filesave.dataaccess.FileSaveProcessor
import java.io.InputStream

class FileSaveController(
    context: Context
) {
    private val downloadsProcessor: FileSaveProcessor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            DownloadsSaveProcessor(context.contentResolver)
        } else {
            DownloadsSaveLegacyProcessor()
        }

    suspend fun savePdfFile(inputStream: InputStream, fileNameWithSuffix: String) {
        downloadsProcessor.save(inputStream, fileNameWithSuffix)
    }
}