package com.chicco.filesave.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import com.chicco.filesave.domain.FileBytesContent
import com.chicco.filesave.domain.FileStreamContent
import com.chicco.filesave.domain.SaveContent
import java.io.File

internal class DownloadsSaveLegacyProcessor(
    private val context: Context,
    private val fileProviderName: String?
) : FileSaveProcessor {

    override fun saveFile(content: FileStreamContent): Uri {
        return content.data.saveToFile(content.fileNameWithSuffix, File(getDirectory(content)))
            .getUriWithFileProviderIfPresent(fileProviderName, context).also {
                it.startMediaScan(context)
            }
    }

    override fun saveFile(content: FileBytesContent): Uri {
        return content.data.saveToFile(content.fileNameWithSuffix, File(getDirectory(content)))
            .getUriWithFileProviderIfPresent(fileProviderName, context).also {
                it.startMediaScan(context)
            }
    }

    private fun getDirectory(content: SaveContent): String {
        return getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
                content.subfolderName?.let { "/$it" }.orEmpty()
    }
}
