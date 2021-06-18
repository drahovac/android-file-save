package com.chicco.filesave.usecase

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.chicco.filesave.domain.FileContent
import com.chicco.filesave.domain.FileSaveResult

class FileSaveController internal constructor(
    private val processors: ProcessorProvider
) {
    companion object {
        fun getInstance(context: Context): FileSaveController {
            return FileSaveController(ProcessorProvider(context))
        }
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    suspend fun saveDocumentFile(content: FileContent): FileSaveResult {
        return processors.downloadsProcessor.save(content)
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    suspend fun saveImageFile(content: FileContent): FileSaveResult {
        return processors.imagesFileSaveProcessor.save(content)
    }
}