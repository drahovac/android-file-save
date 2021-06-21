package com.chicco.filesave.usecase

import android.net.Uri
import com.chicco.filesave.dataaccess.CheckWritePermissionProcessor
import com.chicco.filesave.dataaccess.FileSaveProcessor
import com.chicco.filesave.domain.FileContentTestBuilder
import com.chicco.filesave.domain.FileSaveResult
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
    private val anyCheckWritePermissionProcessor = mock<CheckWritePermissionProcessor>()

    private lateinit var fileSaveController: FileSaveController

    @Before
    fun setUp() {
        whenever(anyProcessorProvider.downloadsProcessor).thenReturn(anyFileDownloadProcessor)
        whenever(anyProcessorProvider.imagesFileSaveProcessor).thenReturn(anyFileImagesProcessor)
        whenever(anyCheckWritePermissionProcessor.hasWritePermission()).thenReturn(true)

        fileSaveController =
            FileSaveController(anyProcessorProvider, anyCheckWritePermissionProcessor)
    }

    @Test
    fun `should request save document file to downloads folder`() {
        runBlocking {
            val anyFileContent = FileContentTestBuilder().build()

            fileSaveController.saveDocumentFile(anyFileContent)

            verify(anyFileDownloadProcessor).save(anyFileContent)
            verify(anyFileImagesProcessor, never()).save(any())
        }
    }

    @Test
    fun `should return save result on save document file to downloads folder`() {
        runBlocking {
            val anyFileContent = FileContentTestBuilder().build()
            whenever(anyFileDownloadProcessor.save(anyFileContent))
                .thenReturn(FileSaveResult.SaveSuccess(anyUri))

            val result = fileSaveController.saveDocumentFile(anyFileContent)

            assertEquals(FileSaveResult.SaveSuccess(anyUri), result)
        }
    }

    @Test
    fun `should request save image file to images folder`() {
        runBlocking {
            val anyFileContent = FileContentTestBuilder().build()

            fileSaveController.saveImageFile(anyFileContent)

            verify(anyFileImagesProcessor).save(anyFileContent)
            verify(anyFileDownloadProcessor, never()).save(any())
        }
    }

    @Test
    fun `should return missing permission result`() {
        whenever(anyCheckWritePermissionProcessor.hasWritePermission()).thenReturn(false)
        runBlocking {
            val anyFileContent = FileContentTestBuilder().build()

            val result = fileSaveController.saveImageFile(anyFileContent)

            assertEquals(FileSaveResult.MissingWritePermission, result)
        }

    }
}