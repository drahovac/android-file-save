package com.chicco.filesave.domain

import java.io.InputStream

data class FileContent(
    val data: InputStream,
    override val fileNameWithoutSuffix: String,
    override val suffix: String,
    override val mimeType: String?,
    override val subfolderName: String?
) : SaveContent