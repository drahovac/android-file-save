package com.chicco.filesave.dataaccess

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

internal class CheckWritePermissionProcessor(private val context: Context) {

    fun hasWritePermission(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}