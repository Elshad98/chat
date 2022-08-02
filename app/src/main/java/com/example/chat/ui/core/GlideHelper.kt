package com.example.chat.ui.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
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
        Glide.with(context)
            .load(SERVER_URL + path?.replace("..", ""))
            .placeholder(placeholder)
            .error(placeholder)
            .into(view)
    }

    fun loadImage(context: Context, path: String?, view: ImageView, @DrawableRes placeholder: Int) {
        loadImage(context, path, view, context.getDrawableCompat(placeholder))
    }

    @JvmStatic
    @BindingAdapter("profileImage")
    fun ImageView.loadImage(image: String?) {
        loadImage(context, image, this)
    }
}
