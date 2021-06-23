package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.chicco.filesave.domain.BitmapContent
import com.chicco.filesave.domain.FileBytesContent
import com.chicco.filesave.domain.FileStreamContent
import com.chicco.filesave.domain.SaveContent

@RequiresApi(Build.VERSION_CODES.Q)
internal class ImageFileSaveProcessor(
    private val contentResolver: ContentResolver,
    private val context: Context
) : FileSaveProcessor, BitmapSaveProcessor {

    private fun getImagesFolderUri(): Uri {
        return MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        )
    }

    override fun saveFile(content: FileStreamContent): Uri {
        with(content) {
            return contentResolver.saveFile(getImagesFolderUri(), getContentDetails(), data).also {
                it.startMediaScan(context)
            }
        }
    }

    override fun saveFile(content: FileBytesContent): Uri {
        with(content) {
            return contentResolver.saveFile(getImagesFolderUri(), getContentDetails(), data).also {
                it.startMediaScan(context)
            }
        }
    }

    private fun SaveContent.getContentDetails(): ContentValues {
        return ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, fileNameWithoutSuffix)
            put(MediaStore.Images.Media.DISPLAY_NAME, fileNameWithoutSuffix)
            mimeType?.let { put(MediaStore.Images.Media.MIME_TYPE, it) }
            subfolderName?.let {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/$it"
                )
            }

            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }
    }

    override fun saveBitmap(content: BitmapContent): Uri {
        val downloadsFolder = getImagesFolderUri()

        with(content) {
            val contentDetails = getContentDetails()

            return contentResolver.saveFile(downloadsFolder, contentDetails) {
                bitmap.compress(format, quality, it)
            }.also {
                it.startMediaScan(context)
            }
        }
    }
}