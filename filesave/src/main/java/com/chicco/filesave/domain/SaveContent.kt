package com.chicco.filesave.domain

import com.chicco.filesave.utils.DOT

interface SaveContent {
    val fileNameWithoutSuffix: String
    val suffix: String
    val mimeType: String?
    val subfolderName: String?

    val fileNameWithSuffix: String
        get() = "$fileNameWithoutSuffix$DOT${suffix}"

}