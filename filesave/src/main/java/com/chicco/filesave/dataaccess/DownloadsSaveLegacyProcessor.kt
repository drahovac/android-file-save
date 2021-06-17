package com.chicco.filesave.dataaccess

import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import com.chicco.filesave.domain.FileContent
import java.io.File

class DownloadsSaveLegacyProcessor : FileSaveProcessor() {

    override fun saveFile(file: FileContent) {
        val directory =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
                    file.subfolderName?.let { "/$it" }.orEmpty()

        file.data.saveToFile(file.fileNameWithSuffix, File(directory))
    }
}