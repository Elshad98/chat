package com.example.chat.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.hideKeyboard() {
    currentFocus?.let { view ->
        getInputMethodManager().hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}