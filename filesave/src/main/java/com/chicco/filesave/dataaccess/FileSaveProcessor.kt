package com.chicco.filesave.dataaccess

import android.net.Uri
import com.chicco.filesave.domain.FileContent
import com.chicco.filesave.domain.FileSaveResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal abstract class FileSaveProcessor {

    suspend fun save(file: FileContent): FileSaveResult {
        return suspendCoroutine { continuation ->
            val result = runCatching {
                saveFile(file)
            }
            continuation.resume(result.toFileSaveResult())
        }
    }

    protected abstract fun saveFile(file: FileContent): Uri
}