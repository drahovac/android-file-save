package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.chicco.filesave.domain.FileSaveResult
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

fun ContentResolver.saveFile(
    folder: Uri,
    contentDetails: ContentValues,
    stream: InputStream
) {
    insert(folder, contentDetails)?.let { contentUri ->
        openFileDescriptor(contentUri, "w")
            .use { parcelFileDescriptor ->
                ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor).write(
                    stream.buffered().readBytes()
                )
            }
    }
}

fun InputStream.saveToFile(
    fileName: String,
    attachmentPath: File
): File {
    use {
        val uniqueFileName = FileNameLegacyResolver.getUniqueFileName(attachmentPath, fileName)
        val savedFile = File(attachmentPath, uniqueFileName)
        try {
            savedFile.parentFile?.mkdirs()

            FileOutputStream(savedFile).use { outputStream ->
                val readBytes = buffered().readBytes()
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

fun Result<Unit>.toFileSaveResult(): FileSaveResult {
    return when {
        isSuccess -> FileSaveResult.SaveSuccess
        else -> FileSaveResult.SaveError(exceptionOrNull())
    }
}