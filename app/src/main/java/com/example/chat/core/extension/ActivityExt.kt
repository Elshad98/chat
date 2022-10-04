package com.example.chat.core.extension

import android.app.Activity

fun Activity.hideKeyboard() {
    currentFocus?.let { view ->
        getInputMethodManager().hideSoftInputFromWindow(view.windowToken, 0)
    }
}