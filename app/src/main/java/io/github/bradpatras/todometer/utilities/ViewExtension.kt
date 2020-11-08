package io.github.bradpatras.todometer.utilities

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    (this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
        it.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun View.showKeyboard() {
    (this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
        it.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}