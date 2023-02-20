package com.example.chat.core.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.chat.BuildConfig

fun ImageView.load(image: String, @DrawableRes placeholder: Int) {
    Glide
        .with(this)
        .load(BuildConfig.BASE_URL_IMAGE + image)
        .placeholder(placeholder)
        .into(this)
}