package com.chicco.filesave.dataaccess

import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import com.chicco.filesave.domain.FileContent
import java.io.File

class ImageFileSaveLegacyProcessor : FileSaveProcessor() {

    override fun saveFile(file: FileContent) {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                .toString() + file.subfolderName?.let { "/$it" }.orEmpty()
        )
        file.data.saveToFile(file.fileNameWithSuffix, directory)
    }
}