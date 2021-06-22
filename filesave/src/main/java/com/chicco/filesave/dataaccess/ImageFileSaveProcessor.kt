package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.chicco.filesave.domain.BitmapContent
import com.chicco.filesave.domain.FileContent

@RequiresApi(Build.VERSION_CODES.Q)
internal class ImageFileSaveProcessor(
    private val contentResolver: ContentResolver
) : FileSaveProcessor, BitmapSaveProcessor {

    private fun getImagesFolderUri(): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    }

    override fun saveFile(file: FileContent): Uri {
        val downloadsFolder = getImagesFolderUri()

        with(file) {
            val contentDetails = ContentValues().apply {
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

            return contentResolver.saveFile(downloadsFolder, contentDetails, data)
        }
    }

    override fun saveBitmap(file: BitmapContent): Uri {
        TODO("Not yet implemented")
    }
}