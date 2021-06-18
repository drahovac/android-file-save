package com.chicco.filesave.dataaccess

import com.chicco.filesave.domain.FileContent
import com.chicco.filesave.domain.FileSaveResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class FileSaveProcessor {

    suspend fun save(file: FileContent): FileSaveResult {
        return suspendCoroutine { continuation ->
            val result = runCatching {
                saveFile(file)
            }
            continuation.resume(result.toFileSaveResult())
        }
    }

    protected abstract fun saveFile(file: FileContent)
}