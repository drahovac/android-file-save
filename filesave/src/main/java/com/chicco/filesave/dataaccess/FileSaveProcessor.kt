package com.chicco.filesave.dataaccess

import android.net.Uri
import com.chicco.filesave.domain.FileBytesContent
import com.chicco.filesave.domain.FileSaveResult
import com.chicco.filesave.domain.FileStreamContent
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal interface FileSaveProcessor {

    suspend fun save(file: FileStreamContent): FileSaveResult {
        return suspendCoroutine { continuation ->
            val result = runCatching {
                saveFile(file)
            }
            continuation.resume(result.toFileSaveResult())
        }
    }

    suspend fun save(file: FileBytesContent): FileSaveResult {
        return suspendCoroutine { continuation ->
            val result = runCatching {
                saveFile(file)
            }
            continuation.resume(result.toFileSaveResult())
        }
    }

    fun saveFile(content: FileStreamContent): Uri

    fun saveFile(content: FileBytesContent): Uri
}