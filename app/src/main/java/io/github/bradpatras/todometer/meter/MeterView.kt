package io.github.bradpatras.todometer.meter

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.bradpatras.todometer.R
import kotlinx.android.synthetic.main.meter_view.view.*
import java.text.SimpleDateFormat
import java.util.*


class MeterView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val rootView = LayoutInflater.from(context).inflate(R.layout.meter_view, this) as ConstraintLayout
    private val dayNumTextView = day_num_tv
    private val dayWeekTextView = day_week_tv
    private val yearTextView = year_tv
    private val monthTextView = month_tv
    private val doneMeterView = done_meter
    private val laterMeterView = later_meter

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
        dayWeekTextView.text = formatter.format(date)

        formatter.applyPattern("yyyy")
        yearTextView.text = formatter.format(date)

        formatter.applyPattern("dd")
        dayNumTextView.text = formatter.format(date)

        formatter.applyPattern("MMM")
        monthTextView.text = formatter.format(date)
    }

    private fun updateMeters() {
        val doneLayout = doneMeterView.layoutParams
        doneLayout.width = (doneMeterProgress * this.width).toInt()
        doneMeterView.layoutParams = doneLayout

        val laterLayout = laterMeterView.layoutParams
        laterLayout.width = (laterMeterProgress * this.width).toInt()
        laterMeterView.layoutParams = laterLayout

        val doneAnim = ValueAnimator.ofInt(doneMeterView.measuredWidth, (doneMeterProgress * this.measuredWidth).toInt())
        doneAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = doneMeterView.layoutParams
            layoutParams.width = value
            doneMeterView.layoutParams = layoutParams
        }
        doneAnim.duration = 250
        doneAnim.start()

        val laterAnim = ValueAnimator.ofInt(laterMeterView.measuredWidth, ((laterMeterProgress + doneMeterProgress) * this.measuredWidth).toInt())
        laterAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = laterMeterView.layoutParams
            layoutParams.width = value
            laterMeterView.layoutParams = layoutParams
        }
        laterAnim.duration = 250
        laterAnim.start()

        if (doneMeterProgress >= 1f) {
            doneMeterView.setBackgroundResource(R.drawable.rounded_green)
        } else {
            doneMeterView.setBackgroundResource(R.drawable.left_rounded_green)
        }

        if (laterMeterProgress + doneMeterProgress >= 1f) {
            laterMeterView.setBackgroundResource(R.drawable.rounded_yellow)
        } else {
            laterMeterView.setBackgroundResource(R.drawable.left_rounded_yellow)
        }
    }
}