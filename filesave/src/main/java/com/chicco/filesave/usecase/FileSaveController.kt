package com.chicco.filesave.usecase

import android.content.Context
import com.chicco.filesave.domain.FileContent

class FileSaveController internal constructor(
    private val processors: ProcessorProvider
) {
    companion object {
        fun getInstance(context: Context): FileSaveController {
            return FileSaveController(ProcessorProvider(context))
        }
    }

    suspend fun saveDocumentFile(content: FileContent) {
        processors.downloadsProcessor.save(content)
    }

    suspend fun saveImageFile(content: FileContent) {
        processors.imagesFileSaveProcessor.save(content)
    }
}