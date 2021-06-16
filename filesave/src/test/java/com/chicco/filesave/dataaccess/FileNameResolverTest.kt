package com.chicco.filesave.dataaccess

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FileNameResolverTest {

    @Mock
    private lateinit var anyContentResolver: ContentResolver

    @Mock
    private lateinit var anyCursor: Cursor
    private lateinit var fileNameResolver: FileNameResolver

    @Before
    fun setUp() {
        whenever(
            anyContentResolver.query(
                eq(ANY_FOLDER_URI),
                eq(arrayOf(ANY_COLUMN_NAME)),
                any(),
                isNull(),
                any()
            )
        ).thenReturn(anyCursor)

        fileNameResolver = FileNameResolver(
            ANY_FOLDER_URI,
            ANY_COLUMN_NAME,
            anyContentResolver
        )
    }

    @Test
    fun `should create query on get file name`() {
        val stringCaptor = argumentCaptor<String>()

        fileNameResolver.getFilenameWithNumber(ANY_FILE_NAME_1)

        verify(anyContentResolver).query(
            eq(ANY_FOLDER_URI),
            eq(arrayOf(ANY_COLUMN_NAME)),
            stringCaptor.capture(),
            isNull(),
            stringCaptor.capture()
        )
        assertEquals("ANY_COLUMN_NAME like 'ANY_FILE_NAME_1%.pdf'", stringCaptor.firstValue)
        assertEquals("ANY_COLUMN_NAME ASC", stringCaptor.secondValue)
    }

    @Test
    fun `should return same file name if no duplicate file found in query`() {
        whenever(anyCursor.count).thenReturn(0)

        val result = fileNameResolver.getFilenameWithNumber(ANY_FILE_NAME_1)

        assertEquals(ANY_FILE_NAME_1, result)
    }

    @Test
    fun `should return file name with incremented number duplicate files found in query`() {
        whenever(anyCursor.count).thenReturn(5)

        val result = fileNameResolver.getFilenameWithNumber(ANY_FILE_NAME_1)

        assertEquals("ANY_FILE_NAME_1(5).pdf", result)
    }

    private companion object {
        val ANY_FOLDER_URI = mock<Uri>()
        const val ANY_COLUMN_NAME = "ANY_COLUMN_NAME"
        const val ANY_FILE_NAME_1 = "ANY_FILE_NAME_1.pdf"
    }
}