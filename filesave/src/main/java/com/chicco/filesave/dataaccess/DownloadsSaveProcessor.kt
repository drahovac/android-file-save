package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.InputStream

@RequiresApi(Build.VERSION_CODES.Q)
internal class DownloadsSaveProcessor(
    private val contentResolver: ContentResolver
) : FileSaveProcessor() {

    private val fileNameResolver: FileNameResolver by lazy {
        FileNameResolver(
            getDownloadFolderUri(),
            MediaStore.Downloads.DISPLAY_NAME,
            contentResolver
        )
    }


    override fun saveFile(fileName: String, stream: InputStream) {
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
        return MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    }
}

