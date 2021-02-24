package io.github.bradpatras.todometer.feature.about

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.utilities.StringHelper
import io.github.bradpatras.todometer.utilities.WebLinkHelper
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val linkHelper: WebLinkHelper,
    private val stringHelper: StringHelper
): ViewModel() {
    fun showTodometerWebpage() {
        linkHelper.openWebLink(stringHelper.getString(R.string.todometer_gh_link))
    }

    fun showTodometerAndroidWebpage() {
        linkHelper.openWebLink(stringHelper.getString(R.string.todometer_android_gh_link))
    }

    fun showLottieWebpage() {
        linkHelper.openWebLink(stringHelper.getString(R.string.lottie_link))
    }
}