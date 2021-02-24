package io.github.bradpatras.todometer.utilities

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(resId: Int): String {
        return context.resources.getString(resId)
    }
}