package com.chicco.filesave.dataaccess

import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class DownloadsSaveLegacyProcessor : FileSaveProcessor() {

    override fun saveFile(fileName: String, stream: InputStream) {
        val downloadsPath =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        getFile(fileName, stream, downloadsPath)
    }

    private fun getFile(
        fileName: String,
        stream: InputStream,
        attachmentPath: File
    ): File {
        val savedFile = File(attachmentPath, fileName)
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