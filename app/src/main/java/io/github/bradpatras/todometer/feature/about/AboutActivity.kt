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

        binding.aboutLottieTv.setOnClickListener {
            binding.confettiAnimationView.apply {
                alpha = 1f
                addAnimatorUpdateListener { animator ->
                    if (animator.animatedFraction >= 1f) {
                        animate().alpha(0f).withEndAction {
                            progress = 0f
                        }.start()
                    }
                }
                playAnimation()
            }
        }
    }
}
