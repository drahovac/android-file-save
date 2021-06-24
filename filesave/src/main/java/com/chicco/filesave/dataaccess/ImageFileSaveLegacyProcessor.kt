package com.chicco.filesave.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import com.chicco.filesave.domain.BitmapContent
import com.chicco.filesave.domain.FileContent
import java.io.File

internal class ImageFileSaveLegacyProcessor(
    private val context: Context,
    private val fileProviderName: String?
) : FileSaveProcessor, BitmapSaveProcessor {

    override fun saveFile(file: FileContent): Uri {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                .toString() + file.subfolderName?.let { "/$it" }.orEmpty()
        )
        return file.data.saveToFile(file.fileNameWithSuffix, directory)
            .getUriWithFileProviderIfPresent(fileProviderName, context).also {
                it.startMediaScan(context)
            }
    }

    override fun saveBitmap(content: BitmapContent): Uri {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                .toString() + content.subfolderName?.let { "/$it" }.orEmpty()
        )
        with(content) {
            val file = saveToFile(fileNameWithSuffix, directory) {
                bitmap.compress(format, quality, it)
            }

            return file.getUriWithFileProviderIfPresent(fileProviderName, context).also {
                it.startMediaScan(context)
            }
        }
    }
}