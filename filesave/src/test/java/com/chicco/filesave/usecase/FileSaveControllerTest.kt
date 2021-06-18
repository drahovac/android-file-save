package com.chicco.filesave.usecase

import com.chicco.filesave.dataaccess.FileSaveProcessor
import com.chicco.filesave.domain.FileContentTestBuilder
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FileSaveControllerTest {

    private val anyProcessorProvider = mock<ProcessorProvider>()
    private val anyFileDownloadProcessor = mock<FileSaveProcessor>()
    private val anyFileImagesProcessor = mock<FileSaveProcessor>()

    private lateinit var fileSaveController: FileSaveController

    @Before
    fun setUp() {
        whenever(anyProcessorProvider.downloadsProcessor).thenReturn(anyFileDownloadProcessor)
        whenever(anyProcessorProvider.imagesFileSaveProcessor).thenReturn(anyFileImagesProcessor)

        fileSaveController = FileSaveController(anyProcessorProvider)
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
    fun `should request save image file to images folder`() {
        runBlocking {
            val anyFileContent = FileContentTestBuilder().build()

            fileSaveController.saveImageFile(anyFileContent)

            verify(anyFileImagesProcessor).save(anyFileContent)
            verify(anyFileDownloadProcessor, never()).save(any())
        }
    }
}