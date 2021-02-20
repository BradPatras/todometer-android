package io.github.bradpatras.todometer.views

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.databinding.MeterViewBinding
import java.text.SimpleDateFormat
import java.util.*

class MeterView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding = MeterViewBinding.inflate(LayoutInflater.from(context))

    var laterMeterProgress: Float = 0.0f
        set(value) {
            field = value
            updateMeters()
        }

    var doneMeterProgress: Float = 0.0f
        set(value) {
            field = value
            updateMeters()
        }

    init {
        val date = Date()

        val formatter = SimpleDateFormat("EEEE", Locale.US)
        binding.dayWeekTv.text = formatter.format(date)

        formatter.applyPattern("yyyy")
        binding.yearTv.text = formatter.format(date)

        formatter.applyPattern("dd")
        binding.dayNumTv.text = formatter.format(date)

        formatter.applyPattern("MMM")
        binding.monthTv.text = formatter.format(date)
    }

    private fun updateMeters() {
        val doneLayout = binding.doneMeter.layoutParams
        doneLayout.width = (doneMeterProgress * this.width).toInt()
        binding.doneMeter.layoutParams = doneLayout

        val laterLayout = binding.laterMeter.layoutParams
        laterLayout.width = (laterMeterProgress * this.width).toInt()
        binding.laterMeter.layoutParams = laterLayout

        val doneAnim = ValueAnimator.ofInt(binding.doneMeter.measuredWidth, (doneMeterProgress * this.measuredWidth).toInt())
        doneAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = binding.doneMeter.layoutParams
            layoutParams.width = value
            binding.doneMeter.layoutParams = layoutParams
        }
        doneAnim.duration = 650
        doneAnim.start()

        val laterAnim = ValueAnimator.ofInt(binding.laterMeter.measuredWidth, ((laterMeterProgress + doneMeterProgress) * this.measuredWidth).toInt())
        laterAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = binding.laterMeter.layoutParams
            layoutParams.width = value
            binding.laterMeter.layoutParams = layoutParams
        }
        laterAnim.duration = 650
        laterAnim.start()

        if (doneMeterProgress >= 1f) {
            binding.doneMeter.setBackgroundResource(R.drawable.rounded_green)
        } else {
            binding.doneMeter.setBackgroundResource(R.drawable.left_rounded_green)
        }

        if (laterMeterProgress + doneMeterProgress >= 1f) {
            binding.laterMeter.setBackgroundResource(R.drawable.rounded_yellow)
        } else {
            binding.laterMeter.setBackgroundResource(R.drawable.left_rounded_yellow)
        }
    }
}