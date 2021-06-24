package com.chicco.filesave.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.core.net.toUri
import com.chicco.filesave.domain.FileBytesContent
import com.chicco.filesave.domain.FileStreamContent
import com.chicco.filesave.domain.SaveContent
import java.io.File

internal class DownloadsSaveLegacyProcessor(
    private val context: Context
) : FileSaveProcessor {

    override fun saveFile(content: FileStreamContent): Uri {
        return content.data.saveToFile(content.fileNameWithSuffix, File(getDirectory(content)))
            .toUri().also {
                it.startMediaScan(context)
            }
    }

    override fun saveFile(content: FileBytesContent): Uri {
        return content.data.saveToFile(content.fileNameWithSuffix, File(getDirectory(content)))
            .toUri().also {
                it.startMediaScan(context)
            }
    }

    private fun getDirectory(content: SaveContent): String {
        val directory =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
                    content.subfolderName?.let { "/$it" }.orEmpty()
        return directory
    }
}