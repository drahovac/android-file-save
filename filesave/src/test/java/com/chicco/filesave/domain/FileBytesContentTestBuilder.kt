package com.chicco.filesave.domain

data class FileBytesContentTestBuilder(
    var data: ByteArray = ByteArray(0),
    var fileNameWithoutSuffix: String = "ANY_FILE_NAME_WITHOUT_SUFFIX",
    var suffix: String = "ANY_SUFFIX",
    var mimeType: String? = "ANY_MIME_TYPE",
    var subfolderName: String? = "ANY_SUBFOLDER"
) {
    fun build() = FileBytesContent(
        data, fileNameWithoutSuffix, suffix, mimeType, subfolderName
    )
}