package com.example.chat.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun ViewGroup.inflate(@LayoutRes resource: Int): View =
    inflater.inflate(resource, this, false)