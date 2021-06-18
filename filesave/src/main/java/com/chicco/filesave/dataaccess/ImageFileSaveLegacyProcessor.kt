package com.chicco.filesave.dataaccess

import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import androidx.core.net.toUri
import com.chicco.filesave.domain.FileContent
import java.io.File

class ImageFileSaveLegacyProcessor : FileSaveProcessor() {

    override fun saveFile(file: FileContent): Uri {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                .toString() + file.subfolderName?.let { "/$it" }.orEmpty()
        )
        return file.data.saveToFile(file.fileNameWithSuffix, directory).toUri()
    }
}