package com.chicco.sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chicco.sample.databinding.ActivityMainBinding
import com.chicco.sample.ui.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar.show()
        downloadPdf.setOnClickListener { model.downloadPdf() }

        model.downloadPdfJob.observe(this) { jobPending ->
            downloadPdf.isEnabled = !jobPending
            if (jobPending) progressBar.show() else progressBar.hide()
        }
    }
}
