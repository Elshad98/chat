package com.example.chat.core.extension

import android.widget.EditText

val EditText.trimmedText: String
    get() = text?.toString()?.trim().orEmpty()