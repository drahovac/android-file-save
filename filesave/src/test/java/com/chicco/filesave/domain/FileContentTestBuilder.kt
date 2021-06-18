package com.chicco.filesave.domain

import com.nhaarman.mockito_kotlin.mock
import java.io.InputStream


data class FileContentTestBuilder(
    var data: InputStream = mock(),
    var fileNameWithoutSuffix: String = "ANY_NAME",
    var suffix: String = "ANY_SUFFIX",
    var mimeType: String? = "ANY_MIME_TYPE",
    var subfolderName: String? = "ANY_SUBFOLDER"
) {
    fun build() = FileContent(
        data, fileNameWithoutSuffix, suffix, mimeType, subfolderName
    )
}