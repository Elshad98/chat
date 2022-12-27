package com.example.chat.core.extension

import android.util.Patterns

private val EMAIL_REGEX = Patterns.EMAIL_ADDRESS.toRegex()
private val PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()

fun String.isValidPassword(): Boolean {
    return isNotEmpty() && PASSWORD_REGEX.matches(this)
}

fun String.isValidEmail(): Boolean {
    return isNotEmpty() && EMAIL_REGEX.matches(this)
}