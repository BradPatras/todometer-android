package io.github.bradpatras.todometer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.databinding.ActivityAboutBinding
import io.github.bradpatras.todometer.utilities.WebLinkHelper

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todometerGhBtn.setOnClickListener {
            WebLinkHelper.openWebLink(this, resources.getString(R.string.todometer_gh_link))
        }

        binding.androidGhBtn.setOnClickListener {
            WebLinkHelper.openWebLink(this, resources.getString(R.string.todometer_android_gh_link))
        }

        binding.lottieBtn.setOnClickListener {
            WebLinkHelper.openWebLink(this, resources.getString(R.string.lottie_link))
        }
    }
}
