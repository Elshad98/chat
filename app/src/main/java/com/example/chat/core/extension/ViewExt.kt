package com.example.chat.core.extension

import android.view.View
import androidx.core.view.isGone

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.toggleVisibility(visible: Boolean = isGone) {
    visibility = if (visible) View.VISIBLE else View.GONE
}