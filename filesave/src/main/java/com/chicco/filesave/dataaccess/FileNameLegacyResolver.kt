package com.chicco.filesave.dataaccess

import java.io.File

object FileNameLegacyResolver {

    private val VERSION_NUMBER_REGEX = "\\(\\d+\\)\$".toRegex()

    fun getUniqueFileName(parent: File, fileName: String): String {
        splitExtension(fileName).run {
            val baseFileName = parseBaseFileName(first)

            parent.listFiles()?.filter {
                it.getBaseFileName() == baseFileName && it.getExtension() == second
            }.let {
                return if (it.orEmpty().isEmpty()) {
                    fileName
                } else {
                    "$baseFileName(${getHighestVersionNumber(it!!)})$second"
                }
            }
        }
    }

    private fun splitExtension(fileName: String): Pair<String, String> {
        val index = fileName.lastIndexOf(".")
        return if (index < fileName.length && index > 0) {
            fileName.substring(0, index) to fileName.substring(index)
        } else fileName to ""
    }

    private fun parseBaseFileName(versionedFileName: String): String {
        versionedFileName.run {
            val hasVersionSuffix =
                VERSION_NUMBER_REGEX.find(versionedFileName)?.value?.isNotEmpty() == true
            return if (hasVersionSuffix) {
                versionedFileName.replaceAfterLast("(", "").dropLast(1)
            } else versionedFileName
        }
    }

    private fun getHighestVersionNumber(filesWithSameName: List<File>): Int {
        return filesWithSameName.map {
            val rawVersionNumber =
                VERSION_NUMBER_REGEX.find(it.getFileNameWithoutExtension())?.value
            rawVersionNumber?.removeFirstAndLastChar()?.toInt() ?: 0
        }.maxOrNull()?.inc() ?: 1
    }

    private fun String.removeFirstAndLastChar() = this.drop(1).dropLast(1)

    private fun File.getBaseFileName() = parseBaseFileName(splitExtension(this.name).first)

    private fun File.getFileNameWithoutExtension() = splitExtension(this.name).first

    private fun File.getExtension() = splitExtension(this.name).second
}