package com.chicco.filesave.usecase

import java.io.InputStream

interface FileSaveController {

    fun savePdfFile(inputStream: InputStream, fileName: String)
}