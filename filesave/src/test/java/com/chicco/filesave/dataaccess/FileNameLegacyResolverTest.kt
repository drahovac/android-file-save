package com.chicco.filesave.dataaccess

import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class FileNameLegacyResolverTest {

    @Before
    fun setUp() {
        whenever(ANY_FILE_1.name).thenReturn(ANY_FILE_1_NAME)
        whenever(ANY_FILE_2.name).thenReturn(ANY_FILE_2_NAME)
        whenever(ANY_FILE_3.name).thenReturn(ANY_FILE_3_NAME)
        whenever(ANY_FILE_4.name).thenReturn(ANY_FILE_4_NAME)
        whenever(ANY_FILE_5.name).thenReturn(ANY_FILE_5_NAME)
        whenever(ANY_FILE_6.name).thenReturn(ANY_FILE_6_NAME)
        whenever(ANY_FILE_7.name).thenReturn(ANY_FILE_7_NAME)
        whenever(ANY_FILE_8.name).thenReturn(ANY_FILE_8_NAME)
        whenever(ANY_FILE_9.name).thenReturn(ANY_FILE_9_NAME)
        whenever(ANY_FILE_10.name).thenReturn(ANY_FILE_10_NAME)
        whenever(ANY_PARENT_FILE.listFiles()).thenReturn(
            arrayOf(
                ANY_FILE_1, ANY_FILE_2, ANY_FILE_3, ANY_FILE_4, ANY_FILE_5,
                ANY_FILE_6, ANY_FILE_7, ANY_FILE_8, ANY_FILE_9, ANY_FILE_10
            )
        )
    }

    @Test
    fun `should return file name with increased version if file with same name exists`() {
        val adaptedFileName =
            FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, ANY_FILE_1_NAME)

        assertEquals("secret-document.something(3).pdf", adaptedFileName)
    }

    @Test
    fun `should return file name with increased version if single file with same name exists`() {
        val adaptedFileName =
            FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, ANY_FILE_2_NAME)

        assertEquals("some_different_name(1).pdf", adaptedFileName)
    }

    @Test
    fun `should return same file name if unique`() {
        val uniqueFileName1 = "different.png"
        val uniqueFileName2 = "necojineho(3).sdjkas.(3).pdf"
        val adaptedFileName1 =
            FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, uniqueFileName1)
        val adaptedFileName2 =
            FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, uniqueFileName2)

        assertEquals(uniqueFileName1, adaptedFileName1)
        assertEquals(uniqueFileName2, adaptedFileName2)
    }

    @Test
    fun `should set highest version for unordered file versions`() {
        val adaptedFileName = FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, "un_ordered.version.number.png")

        assertEquals("un_ordered.version.number(17).png", adaptedFileName)
    }

    @Test
    fun `should replace version for correct file versions`() {
        val adaptedFileName = FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, ANY_FILE_3_NAME)

        assertEquals("secret-document.something(3).pdf", adaptedFileName)
    }

    @Test
    fun `should set file name for empty directory`() {
        whenever(ANY_PARENT_FILE.listFiles()).thenReturn(arrayOf())
        val adaptedFileName = FileNameLegacyResolver.getUniqueFileName(ANY_PARENT_FILE, ANY_FILE_3_NAME)

        assertEquals(ANY_FILE_3_NAME, adaptedFileName)
    }

    private companion object {
        val ANY_PARENT_FILE = Mockito.mock(File::class.java)!!
        val ANY_FILE_1 = Mockito.mock(File::class.java)!!
        val ANY_FILE_2 = Mockito.mock(File::class.java)!!
        val ANY_FILE_3 = Mockito.mock(File::class.java)!!
        val ANY_FILE_4 = Mockito.mock(File::class.java)!!
        val ANY_FILE_5 = Mockito.mock(File::class.java)!!
        val ANY_FILE_6 = Mockito.mock(File::class.java)!!
        val ANY_FILE_7 = Mockito.mock(File::class.java)!!
        val ANY_FILE_8 = Mockito.mock(File::class.java)!!
        val ANY_FILE_9 = Mockito.mock(File::class.java)!!
        val ANY_FILE_10 = Mockito.mock(File::class.java)!!
        const val ANY_FILE_1_NAME = "secret-document.something.pdf"
        const val ANY_FILE_2_NAME = "some_different_name.pdf"
        const val ANY_FILE_3_NAME = "secret-document.something(1).pdf"
        const val ANY_FILE_4_NAME = "secret-document.something(2).pdf"
        const val ANY_FILE_5_NAME = "secret-document.something(1)continues.pdf"
        const val ANY_FILE_6_NAME = "secret-document.something(1)continues(1).pdf"
        const val ANY_FILE_7_NAME = "háčky.čárky.png"
        const val ANY_FILE_8_NAME = "some_different_name.png"
        const val ANY_FILE_9_NAME = "un_ordered.version.number(5).png"
        const val ANY_FILE_10_NAME = "un_ordered.version.number(16).png"
    }

}