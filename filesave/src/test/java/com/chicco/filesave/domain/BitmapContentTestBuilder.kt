package com.chicco.filesave.domain

import android.graphics.Bitmap
import com.nhaarman.mockito_kotlin.mock

data class BitmapContentTestBuilder(
    var bitmap: Bitmap = mock(),
    var format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    var quality: Int = 100
) {
    fun build() = BitmapContent(bitmap, format, quality)
}