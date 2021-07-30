package com.example.chat.cache

import android.content.SharedPreferences
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.exception.Failure
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val prefs: SharedPreferences
) {

    companion object {

        const val ACCOUNT_TOKEN = "account_token"
    }

    fun saveToken(token: String): Either<Failure, None> {
        prefs.edit().apply {
            putString(ACCOUNT_TOKEN, token)
        }.apply()

        return Either.Right(None())
    }

    fun getToken(): Either<Failure, String> {
        return Either.Right(prefs.getString(ACCOUNT_TOKEN, "")!!)
    }
}