package com.chicco.sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chicco.sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savePdfStream.setOnClickListener { model.savePdf(saveAsBytes = false) }
        savePdfBytes.setOnClickListener { model.savePdf(saveAsBytes = true) }
        saveImage.setOnClickListener { model.saveImage() }
        saveBitmap.setOnClickListener { model.saveBitmap() }

        model.downloadPendingJob.observe(this) { jobPending ->
            savePdfStream.isEnabled = !jobPending
            if (jobPending) progressBar.show() else progressBar.hide()
        }
        model.pdfSaveResult.observe(this) {
            savePdfResult.text = it
        }
        model.imageSaveResult.observe(this) {
            saveImageResult.text = it
        }
        model.bitmapSaveResult.observe(this) {
            saveBitmapResult.text = it
        }
    }
}
