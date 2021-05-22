package com.chicco.filesave.usecase

import android.content.Context
import com.chicco.filesave.dataaccess.FileSaveProcessor
import java.io.InputStream

class FileSaveController(
    context: Context
) {

    private val processor = FileSaveProcessor(context.contentResolver)

    suspend fun savePdfFile(inputStream: InputStream, fileName: String) {
        processor.saveToDownloadsFolder(inputStream, fileName)
    }
}