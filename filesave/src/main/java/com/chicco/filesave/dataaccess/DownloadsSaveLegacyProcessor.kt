package com.chicco.filesave.dataaccess

import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import com.chicco.filesave.domain.FileContent
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class DownloadsSaveLegacyProcessor : FileSaveProcessor() {

    override fun saveFile(file: FileContent) {
        val directory =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
                    file.subfolderName?.let { "/$it" }.orEmpty()
        val fileName = file.fileNameWithSuffix

        getFile(fileName, file.data, File(directory))
    }

    private fun getFile(
        fileName: String,
        stream: InputStream,
        attachmentPath: File
    ): File {
        val uniqueFileName = FileNameLegacyResolver.getUniqueFileName(attachmentPath, fileName)
        val savedFile = File(attachmentPath, uniqueFileName)
        try {
            savedFile.parentFile?.mkdirs()

            FileOutputStream(savedFile).use { outputStream ->
                val readBytes = stream.buffered().readBytes()
                outputStream.write(readBytes)
                outputStream.flush()
            }
        } catch (e: IOException) {
            savedFile.delete()
            e.printStackTrace()
            throw e
        }
        return savedFile
    }
}