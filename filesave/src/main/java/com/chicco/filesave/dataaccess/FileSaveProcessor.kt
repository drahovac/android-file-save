package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import java.io.*

internal class FileSaveProcessor(
    private val contentResolver: ContentResolver
) {

    fun saveToDownloadsFolder(stream: InputStream, fileName: String) {
        val downloadsFolder = getDownloadFolderUri()
        val contentDetails = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
        }

        contentResolver.insert(downloadsFolder, contentDetails)?.let { contentUri ->
            contentResolver.openFileDescriptor(contentUri, "w").use { parcelFileDescriptor ->
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
}