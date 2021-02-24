package io.github.bradpatras.todometer.feature.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.databinding.ActivityAboutBinding
import io.github.bradpatras.todometer.utilities.WebLinkHelper

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    private val viewModel: AboutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todometerGhBtn.setOnClickListener {
            viewModel.showTodometerWebpage()
        }

        binding.androidGhBtn.setOnClickListener {
            viewModel.showTodometerAndroidWebpage()
        }

        binding.lottieBtn.setOnClickListener {
            viewModel.showLottieWebpage()
        }
    }
}
