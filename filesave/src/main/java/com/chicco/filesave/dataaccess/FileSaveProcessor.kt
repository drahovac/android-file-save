package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import com.chicco.filesave.utils.suffixOrNull
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class FileSaveProcessor(
    private val contentResolver: ContentResolver
) {

    private val fileNameResolver: FileNameResolver by lazy {
        FileNameResolver(
            getDownloadFolderUri(),
            MediaStore.Downloads.DISPLAY_NAME,
            contentResolver
        )
    }

    suspend fun saveToDownloadsFolder(stream: InputStream, fileNameWithSuffix: String): Boolean {
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

    private fun saveFile(fileName: String, stream: InputStream) {
        val downloadsFolder = getDownloadFolderUri()
        val resolvedFileName = fileNameResolver.getFilenameWithNumber(fileName)

        val contentDetails = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, resolvedFileName)
        }

        contentResolver.insert(downloadsFolder, contentDetails)?.let { contentUri ->
            contentResolver.openFileDescriptor(contentUri, "w")
                .use { parcelFileDescriptor ->
                    ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor).write(
                        stream.buffered().readBytes()
                    )
                }
        }
    }

    private fun getDownloadFolderUri(): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            TODO("VERSION.SDK_INT < Q")
        }
    }

    private val folderUri: Uri?
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Downloads.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }
        }
}

