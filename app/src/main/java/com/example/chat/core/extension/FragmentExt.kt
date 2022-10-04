package com.example.chat.core.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: CharSequence) {
    requireActivity().showToast(text)
}

fun Fragment.showToast(@StringRes resId: Int) {
    requireActivity().showToast(resId)
}