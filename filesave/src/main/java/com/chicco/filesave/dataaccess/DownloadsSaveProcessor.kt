package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.chicco.filesave.domain.FileContent

@RequiresApi(Build.VERSION_CODES.Q)
internal class DownloadsSaveProcessor(
    private val contentResolver: ContentResolver,
    private val context: Context
) : FileSaveProcessor {

    override fun saveFile(file: FileContent): Uri {
        val downloadsFolder = getDownloadFolderUri()

        with(file) {
            val contentDetails = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileNameWithSuffix)
                mimeType?.let { put(MediaStore.Downloads.MIME_TYPE, it) }
                subfolderName?.let {
                    put(
                        MediaStore.Downloads.RELATIVE_PATH,
                        "${Environment.DIRECTORY_DOWNLOADS}/$it"
                    )
                }
            }

            return contentResolver.saveFile(downloadsFolder, contentDetails, data).also {
                it.startMediaScan(context)
            }
        }
    }

    private fun getDownloadFolderUri(): Uri {
        return MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    }
}