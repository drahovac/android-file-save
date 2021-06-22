package com.chicco.filesave.dataaccess

import android.net.Uri
import com.chicco.filesave.domain.BitmapContent
import com.chicco.filesave.domain.FileSaveResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface BitmapSaveProcessor {

    suspend fun save(content: BitmapContent): FileSaveResult {
        return suspendCoroutine { continuation ->
            val result = runCatching {
                saveBitmap(content)
            }
            continuation.resume(result.toFileSaveResult())
        }
    }

    fun saveBitmap(file: BitmapContent): Uri
}