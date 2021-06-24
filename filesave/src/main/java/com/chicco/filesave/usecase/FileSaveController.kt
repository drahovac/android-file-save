package com.chicco.filesave.usecase

import android.content.Context
import com.chicco.filesave.dataaccess.CheckWritePermissionProcessor
import com.chicco.filesave.dataaccess.FileSaveProcessor
import com.chicco.filesave.domain.BitmapContent
import com.chicco.filesave.domain.FileBytesContent
import com.chicco.filesave.domain.FileSaveResult
import com.chicco.filesave.domain.FileStreamContent

class FileSaveController internal constructor(
    private val processors: ProcessorProvider,
    private val checkPermissionProcessor: CheckWritePermissionProcessor,
) {
    companion object {

        /*
         * @param fileProviderName name of file provider for apis with sdk < Q
         * if sharing file intent needed
         */
        fun getInstance(context: Context, fileProviderName: String? = null): FileSaveController {
            return FileSaveController(
                ProcessorProvider(context, fileProviderName),
                CheckWritePermissionProcessor(context)
            )
        }
    }

    suspend fun saveDocumentFile(content: FileStreamContent): FileSaveResult {
        return saveFileStreamIfPermissionGranted(content, processors.downloadsProcessor)
    }

    suspend fun saveDocumentFile(content: FileBytesContent): FileSaveResult {
        return saveFileBytesIfPermissionGranted(content, processors.downloadsProcessor)
    }

    suspend fun saveImageFile(content: FileStreamContent): FileSaveResult {
        return saveFileStreamIfPermissionGranted(content, processors.imagesFileSaveProcessor)
    }

    suspend fun saveImageFile(content: FileBytesContent): FileSaveResult {
        return saveFileBytesIfPermissionGranted(content, processors.imagesFileSaveProcessor)
    }

    suspend fun saveBitmap(content: BitmapContent): FileSaveResult {
        return saveIfPermissionGranted { processors.bitmapSaveProcessor.save(content) }
    }

    private suspend fun saveFileStreamIfPermissionGranted(
        content: FileStreamContent,
        processor: FileSaveProcessor
    ): FileSaveResult {
        return saveIfPermissionGranted {
            processor.save(content)
        }
    }

    private suspend fun saveFileBytesIfPermissionGranted(
        content: FileBytesContent,
        processor: FileSaveProcessor
    ): FileSaveResult {
        return saveIfPermissionGranted {
            processor.save(content)
        }
    }

    private suspend fun saveIfPermissionGranted(
        saveAction: suspend () -> FileSaveResult
    ): FileSaveResult {
        return if (checkPermissionProcessor.hasWritePermission()) {
            saveAction()
        } else FileSaveResult.MissingWritePermission
    }
}