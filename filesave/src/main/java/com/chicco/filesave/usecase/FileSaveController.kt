package com.chicco.filesave.usecase

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.chicco.filesave.domain.FileContent

class FileSaveController internal constructor(
    private val processors: ProcessorProvider
) {
    companion object {
        fun getInstance(context: Context): FileSaveController {
            return FileSaveController(ProcessorProvider(context))
        }
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    suspend fun saveDocumentFile(content: FileContent) {
        processors.downloadsProcessor.save(content)
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    suspend fun saveImageFile(content: FileContent) {
        processors.imagesFileSaveProcessor.save(content)
    }
}