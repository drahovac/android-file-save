package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.chicco.filesave.domain.FileContent

class ImageFileSaveProcessor(
    private val contentResolver: ContentResolver
) : FileSaveProcessor() {

    private fun getImagesFolderUri(): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun saveFile(file: FileContent) {
        val downloadsFolder = getImagesFolderUri()

        with(file) {
            val contentDetails = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, fileNameWithoutSuffix)
                put(MediaStore.Images.Media.DISPLAY_NAME, fileNameWithoutSuffix)
                mimeType?.let { put(MediaStore.Images.Media.MIME_TYPE, it) }
                subfolderName?.let {
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        PICTURES_FOLDER_NAME + subfolderName
                    )
                }

                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            }

            contentResolver.saveFile(downloadsFolder, contentDetails, data)
        }
    }

    private companion object {
        private const val PICTURES_FOLDER_NAME = "Pictures/"
    }
}