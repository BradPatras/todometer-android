package io.github.bradpatras.todometer.utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

class WebLinkHelper {
    companion object {
        fun openWebLink(context: Context, url: String) {
            val webLink: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webLink)
            if (intent.resolveActivity(context.packageManager) != null) {
                startActivity(context, intent, null)
            }
        }
    }
}