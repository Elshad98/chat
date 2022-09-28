package com.example.chat.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: CharSequence) {
    requireActivity().showToast(text)
}

fun Fragment.showToast(@StringRes resId: Int) {
    requireActivity().showToast(resId)
}