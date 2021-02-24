package io.github.bradpatras.todometer.utilities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WebLinkHelper @Inject constructor(@ApplicationContext private val context: Context) {
    fun openWebLink(url: String) {
        val webLink: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webLink)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            // no web browser available to handle opening links
        }
    }
}