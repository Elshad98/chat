package com.example.chat.core.extension

import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

val Fragment.supportActionBar: ActionBar?
    get() = (requireActivity() as AppCompatActivity).supportActionBar

fun Fragment.showToast(text: CharSequence) {
    requireActivity().showToast(text)
}

fun Fragment.showToast(@StringRes resId: Int) {
    requireActivity().showToast(resId)
}

fun Fragment.setSupportActionBar(toolbar: Toolbar) {
    (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
}