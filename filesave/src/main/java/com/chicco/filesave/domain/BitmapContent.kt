package com.chicco.filesave.domain

import android.graphics.Bitmap

data class BitmapContent(
    val bitmap: Bitmap,
    val format: Bitmap.CompressFormat,
    val quality: Int = 100,
    override val fileNameWithoutSuffix: String,
    override val suffix: String,
    override val mimeType: String?,
    override val subfolderName: String?
) : SaveContent