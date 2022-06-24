package com.example.chat.extensions

import android.view.View

val View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE

val View.isVisible: Boolean
    get() = visibility == View.VISIBLE

val View.isGone: Boolean
    get() = visibility == View.GONE

fun View.invisible() = run { visibility = View.INVISIBLE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.gone() = run { visibility = View.GONE }

fun View.toggleVisibility(visible: Boolean = isGone) =
    run { visibility = if (visible) View.VISIBLE else View.GONE }