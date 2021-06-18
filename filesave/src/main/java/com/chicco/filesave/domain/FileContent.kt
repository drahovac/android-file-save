package com.chicco.filesave.domain

import com.chicco.filesave.utils.DOT
import java.io.InputStream

data class FileContent(
    val data: InputStream,
    val fileNameWithoutSuffix: String,
    val suffix: String,
    val mimeType: String?,
    val subfolderName: String?
) {
    val fileNameWithSuffix: String
        get() = "$fileNameWithoutSuffix$DOT${suffix}"
}