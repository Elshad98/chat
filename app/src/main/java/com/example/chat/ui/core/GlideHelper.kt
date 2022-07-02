package com.example.chat.ui.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.chat.extensions.getDrawableCompat

object GlideHelper {

    private const val SERVER_URL = "http://n964182b.bget.ru"

    fun loadImage(
        context: Context,
        path: String?,
        view: ImageView,
        placeholder: Drawable? = view.drawable
    ) {
        val imgPath = SERVER_URL + path?.replace("..", "")

        Glide.with(context)
            .load(imgPath)
            .placeholder(placeholder)
            .error(placeholder)
            .into(view)
    }

    fun loadImage(context: Context, path: String?, view: ImageView, @DrawableRes placeholder: Int) {
        loadImage(context, path, view, context.getDrawableCompat(placeholder))
    }
}
