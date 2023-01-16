package com.example.chat.core.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.load(path: String, @DrawableRes placeholder: Int) {
    Glide
        .with(this)
        .load(path)
        .placeholder(placeholder)
        .into(this)
}