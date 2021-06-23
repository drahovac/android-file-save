package com.chicco.filesave.domain

import android.graphics.Bitmap
import com.nhaarman.mockito_kotlin.mock

data class BitmapContentTestBuilder(
    var bitmap: Bitmap = mock(),
    var format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    var quality: Int = 100,
    var fileNameWithoutSuffix: String = "ANY_BITMAP_NAME",
    var suffix: String = "ANY_SUFFIX",
    var mimeType: String? = "ANY_MIME_TYPE",
    var subfolderName: String? = "ANY_SUBFOLDER"
) {
    fun build() = BitmapContent(
        bitmap,
        format,
        quality,
        fileNameWithoutSuffix,
        suffix,
        mimeType,
        subfolderName
    )
}