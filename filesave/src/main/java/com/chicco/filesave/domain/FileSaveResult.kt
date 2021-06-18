package com.chicco.filesave.domain

sealed interface FileSaveResult {

    object SaveSuccess : FileSaveResult

    object MissingWritePermission : FileSaveResult

    data class SaveError(val exception: Throwable?) : FileSaveResult
}
