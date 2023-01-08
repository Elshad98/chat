package com.example.chat.core.extension

import android.util.Patterns

private val EMAIL_REGEX = Patterns.EMAIL_ADDRESS.toRegex()
private val USERNAME_REGEX = "^(?=.{3,32}\$)(?![_.])(?!.*[_.]{2})[a-zA-Zа-яА-Я0-9._]+(?<![_.])\$".toRegex()
private val PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$".toRegex()

fun String.isValidUsername(): Boolean {
    return isNotEmpty() && USERNAME_REGEX.matches(this)
}

fun String.isValidPassword(): Boolean {
    return isNotEmpty() && PASSWORD_REGEX.matches(this)
}

fun String.isValidEmail(): Boolean {
    return isNotEmpty() && EMAIL_REGEX.matches(this)
}