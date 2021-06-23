package com.chicco.filesave.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import androidx.core.net.toUri
import com.chicco.filesave.domain.BitmapContent
import com.chicco.filesave.domain.FileBytesContent
import com.chicco.filesave.domain.FileStreamContent
import com.chicco.filesave.domain.SaveContent
import java.io.File

internal class ImageFileSaveLegacyProcessor(
    private val context: Context
) : FileSaveProcessor, BitmapSaveProcessor {

    override fun saveFile(content: FileStreamContent): Uri {
        return content.data.saveToFile(content.fileNameWithSuffix, getDirectory(content)).toUri()
            .also {
                it.startMediaScan(context)
            }
    }

    override fun saveFile(content: FileBytesContent): Uri {
        return content.data.saveToFile(content.fileNameWithSuffix, getDirectory(content)).toUri()
            .also {
                it.startMediaScan(context)
            }
    }

    private fun getDirectory(content: SaveContent) = File(
        Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
            .toString() + content.subfolderName?.let { "/$it" }.orEmpty()
    )

    override fun saveBitmap(content: BitmapContent): Uri {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                .toString() + content.subfolderName?.let { "/$it" }.orEmpty()
        )
        with(content) {
            return saveToFile(fileNameWithSuffix, directory) {
                bitmap.compress(format, quality, it)
            }.toUri().also {
                it.startMediaScan(context)
            }
        }
    }
}