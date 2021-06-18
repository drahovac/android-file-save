package com.chicco.filesave.dataaccess

import com.chicco.filesave.domain.FileContent
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class FileSaveProcessor {

    suspend fun save(file: FileContent): Boolean {
        return suspendCoroutine { continuation ->
            val result = kotlin.runCatching {
                saveFile(file)
            }.isSuccess
            continuation.resume(result)
        }
    }

    protected abstract fun saveFile(file: FileContent)
}