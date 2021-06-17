package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.ParcelFileDescriptor
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