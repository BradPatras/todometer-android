package io.github.bradpatras.todometer.meter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import io.github.bradpatras.todometer.R
import kotlinx.android.synthetic.main.meter_view.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MeterView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    val rootView = LayoutInflater.from(context).inflate(R.layout.meter_view, this) as ConstraintLayout
    val dayNumTextView = day_num_tv
    val dayWeekTextView = day_week_tv
    val yearTextView = year_tv
    val monthTextView = month_tv
    val doneMeterView = done_meter
    val laterMeterView = later_meter

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
}