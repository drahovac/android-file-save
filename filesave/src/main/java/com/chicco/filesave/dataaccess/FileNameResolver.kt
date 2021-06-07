package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.net.Uri
import com.chicco.filesave.utils.DOT
import com.chicco.filesave.utils.suffixOrEmpty

class FileNameResolver(
    private val folderUri: Uri,
    private val displayNameColumnName: String,
    private val contentResolver: ContentResolver
) {
    fun getFilenameWithNumber(filename: String): String {
        val filenameStart = filename.substringBeforeLast(DOT)
        val suffix = filename.suffixOrEmpty()

        createQuery(
            filenameStart,
            suffix
        )?.use { cursor ->
            val count = cursor.count
            return if (count == 0) {
                filename
            } else {
                "$filenameStart($count).$suffix"
            }
        }
        return filename
    }

    private fun createQuery(filenameStart: String, suffix: String) = contentResolver.query(
        folderUri,
        arrayOf(displayNameColumnName),
        getSelection(filenameStart, suffix),
        null,
        "$displayNameColumnName ASC"
    )

    private fun getSelection(filename: String, suffix: String): String {
        return "$displayNameColumnName like '$filename%.$suffix'"
    }
}