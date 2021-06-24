package com.chicco.filesave.usecase

import android.net.Uri
import com.chicco.filesave.dataaccess.BitmapSaveProcessor
import com.chicco.filesave.dataaccess.CheckWritePermissionProcessor
import com.chicco.filesave.dataaccess.FileSaveProcessor
import com.chicco.filesave.domain.*
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FileSaveControllerTest {

    private val anyUri = mock<Uri>()
    private val anyProcessorProvider = mock<ProcessorProvider>()
    private val anyFileDownloadProcessor = mock<FileSaveProcessor>()
    private val anyFileImagesProcessor = mock<FileSaveProcessor>()
    private val anyBitmapProcessor = mock<BitmapSaveProcessor>()
    private val anyCheckWritePermissionProcessor = mock<CheckWritePermissionProcessor>()

    private lateinit var fileSaveController: FileSaveController

    @Before
    fun setUp() {
        whenever(anyProcessorProvider.downloadsProcessor).thenReturn(anyFileDownloadProcessor)
        whenever(anyProcessorProvider.imagesFileSaveProcessor).thenReturn(anyFileImagesProcessor)
        whenever(anyProcessorProvider.bitmapSaveProcessor).thenReturn(anyBitmapProcessor)
        whenever(anyCheckWritePermissionProcessor.hasWritePermission()).thenReturn(true)

        fileSaveController =
            FileSaveController(anyProcessorProvider, anyCheckWritePermissionProcessor)
    }

    @Test
    fun `should request save document file to downloads folder`() {
        runBlocking {
            val anyFileContent = FileStreamContentTestBuilder().build()

            fileSaveController.saveDocumentFile(anyFileContent)

            verify(anyFileDownloadProcessor).save(anyFileContent)
            verify(anyFileImagesProcessor, never()).save(any<FileStreamContent>())
        }
    }

    @Test
    fun `should request save document file bytes to downloads folder`() {
        runBlocking {
            val anyFileContent = FileBytesContentTestBuilder().build()

            fileSaveController.saveDocumentFile(anyFileContent)

            verify(anyFileDownloadProcessor).save(anyFileContent)
            verify(anyFileImagesProcessor, never()).save(any<FileBytesContent>())
        }
    }

    @Test
    fun `should return save result on save document file to downloads folder`() {
        runBlocking {
            val anyFileContent = FileStreamContentTestBuilder().build()
            whenever(anyFileDownloadProcessor.save(anyFileContent))
                .thenReturn(FileSaveResult.SaveSuccess(anyUri))

            val result = fileSaveController.saveDocumentFile(anyFileContent)

            assertEquals(FileSaveResult.SaveSuccess(anyUri), result)
        }
    }

    @Test
    fun `should request save image file to images folder`() {
        runBlocking {
            val anyFileContent = FileStreamContentTestBuilder().build()

            fileSaveController.saveImageFile(anyFileContent)

            verify(anyFileImagesProcessor).save(anyFileContent)
            verify(anyFileDownloadProcessor, never()).save(any<FileStreamContent>())
        }
    }


    @Test
    fun `should request save image bytes to images folder`() {
        runBlocking {
            val anyFileContent = FileBytesContentTestBuilder().build()

            fileSaveController.saveImageFile(anyFileContent)

            verify(anyFileImagesProcessor).save(anyFileContent)
            verify(anyFileDownloadProcessor, never()).save(any<FileBytesContent>())
        }
    }

    @Test
    fun `should return missing permission result`() {
        whenever(anyCheckWritePermissionProcessor.hasWritePermission()).thenReturn(false)
        runBlocking {
            val anyFileContent = FileStreamContentTestBuilder().build()

            val result = fileSaveController.saveImageFile(anyFileContent)

            assertEquals(FileSaveResult.MissingWritePermission, result)
        }
    }

    @Test
    fun `should request save bitmap`() {
        runBlocking {
            val anyBitmapContent = BitmapContentTestBuilder().build()

            fileSaveController.saveBitmap(anyBitmapContent)

            verify(anyBitmapProcessor).save(anyBitmapContent)
            verify(anyFileDownloadProcessor, never()).save(any<FileStreamContent>())
            verify(anyFileImagesProcessor, never()).save(any<FileStreamContent>())
        }
    }
}