package com.chicco.filesave.dataaccess

import com.chicco.filesave.utils.suffixOrNull
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class FileSaveProcessor {

    suspend fun save(stream: InputStream, fileNameWithSuffix: String): Boolean {
        check(fileNameWithSuffix.suffixOrNull() != null) {
            "File name: $fileNameWithSuffix must contain suffix."
        }
        return suspendCoroutine { continuation ->
            val result = kotlin.runCatching {
                saveFile(fileNameWithSuffix, stream)
            }.isSuccess
            continuation.resume(result)
        }
    }

    protected abstract fun saveFile(fileName: String, stream: InputStream)
}