package io.github.bradpatras.todometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.bradpatras.todometer.utilities.WebLinkHelper
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        todometer_gh_btn.setOnClickListener {
            WebLinkHelper.openWebLink(this, resources.getString(R.string.todometer_gh_link))
        }

        android_gh_btn.setOnClickListener {
            WebLinkHelper.openWebLink(this, resources.getString(R.string.todometer_android_gh_link))
        }
    }
}
