package com.chicco.filesave.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class FileContentTest {

    private companion object {
        const val ANY_NAME = "ANY_NAME"
        const val ANY_SUFFIX = "ANY_SUFFIX"
        val ANY_FILE_CONTENT = FileStreamContentTestBuilder(
            fileNameWithoutSuffix  = ANY_NAME,
            suffix = ANY_SUFFIX
        ).build()
    }

    @Test
    fun `should return properly formatted name with suffix`() {
        assertEquals("ANY_NAME.ANY_SUFFIX", ANY_FILE_CONTENT.fileNameWithSuffix)
    }
}