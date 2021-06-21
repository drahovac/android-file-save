package com.chicco.filesave.usecase

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.chicco.filesave.dataaccess.CheckWritePermissionProcessor
import com.chicco.filesave.dataaccess.FileSaveProcessor
import com.chicco.filesave.domain.FileContent
import com.chicco.filesave.domain.FileSaveResult

class FileSaveController internal constructor(
    private val processors: ProcessorProvider,
    private val checkPermissionProcessor: CheckWritePermissionProcessor
) {
    companion object {
        fun getInstance(context: Context): FileSaveController {
            return FileSaveController(
                ProcessorProvider(context),
                CheckWritePermissionProcessor(context)
            )
        }
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    suspend fun saveDocumentFile(content: FileContent): FileSaveResult {
        return saveFileIfPermissionGranted(content, processors.downloadsProcessor)
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    suspend fun saveImageFile(content: FileContent): FileSaveResult {
        return saveFileIfPermissionGranted(content, processors.imagesFileSaveProcessor)
    }

    private suspend fun saveFileIfPermissionGranted(
        content: FileContent,
        processor: FileSaveProcessor
    ): FileSaveResult {
        return if (checkPermissionProcessor.hasWritePermission()) {
            processor.save(content)
        } else FileSaveResult.MissingWritePermission
    }
}