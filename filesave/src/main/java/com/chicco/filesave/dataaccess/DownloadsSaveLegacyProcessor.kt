package com.chicco.filesave.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import com.chicco.filesave.domain.FileContent
import java.io.File

internal class DownloadsSaveLegacyProcessor(
    private val context: Context,
    private val fileProviderName: String?
) : FileSaveProcessor {

    override fun saveFile(content: FileContent): Uri {
        val directory =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
                    content.subfolderName?.let { "/$it" }.orEmpty()
        val uri = getUriFromFile(content, directory)

        uri.startMediaScan(context)

        return uri
    }

    private fun getUriFromFile(
        content: FileContent,
        directory: String
    ): Uri {
        val file = content.data.saveToFile(content.fileNameWithSuffix, File(directory))
        return file.getUriWithFileProviderIfPresent(fileProviderName, context)
    }
}