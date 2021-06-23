package com.chicco.filesave.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.core.net.toUri
import com.chicco.filesave.domain.FileContent
import java.io.File

internal class DownloadsSaveLegacyProcessor(
    private val context: Context
) : FileSaveProcessor {

    override fun saveFile(file: FileContent): Uri {
        val directory =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
                    file.subfolderName?.let { "/$it" }.orEmpty()

        return file.data.saveToFile(file.fileNameWithSuffix, File(directory)).toUri().also {
            it.startMediaScan(context)
        }
    }
}