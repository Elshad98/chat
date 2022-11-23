package com.example.chat.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.local.model.UserEntity
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val preferences: SharedPreferences
) {

    companion object {

        private const val PREF_USER_ID = "user_id"
        private const val PREF_USER_NAME = "user_name"
        private const val PREF_USER_DATA = "user_data"
        private const val PREF_USER_EMAIL = "user_email"
        private const val PREF_USER_IMAGE = "user_image"
        private const val PREF_USER_TOKEN = "user_token"
        private const val PREF_USER_STATUS = "user_status"
        private const val PREF_USER_PASSWORD = "user_password"
    }

    fun getToken(): Either.Right<String> {
        return Either.Right(preferences.getString(PREF_USER_TOKEN, "")!!)
    }

    fun saveToken(token: String): Either.Right<None> {
        preferences.edit {
            putString(PREF_USER_TOKEN, token)
        }
        return Either.Right(None())
    }

    fun saveUser(user: UserEntity): Either.Right<None> {
        preferences.edit {
            putSafely(PREF_USER_ID, user.id)
            putSafely(PREF_USER_NAME, user.name)
            putSafely(PREF_USER_EMAIL, user.email)
            putSafely(PREF_USER_TOKEN, user.token)
            putSafely(PREF_USER_IMAGE, user.image)
            putString(PREF_USER_STATUS, user.status)
            putSafely(PREF_USER_DATA, user.userData)
            putSafely(PREF_USER_PASSWORD, user.password)
        }
        return Either.Right(None())
    }

    fun getUser(): Either<Failure, UserEntity> {
        val id = preferences.getLong(PREF_USER_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }

        val user = UserEntity(
            id = id,
            userData = preferences.getLong(PREF_USER_DATA, 0),
            name = preferences.getString(PREF_USER_NAME, "")!!,
            email = preferences.getString(PREF_USER_EMAIL, "")!!,
            image = preferences.getString(PREF_USER_IMAGE, "")!!,
            token = preferences.getString(PREF_USER_TOKEN, "")!!,
            status = preferences.getString(PREF_USER_STATUS, "")!!,
            password = preferences.getString(PREF_USER_PASSWORD, "")!!
        )
        return Either.Right(user)
    }

    fun clear(): Either.Right<None> {
        preferences.edit {
            clear()
        }
        return Either.Right(None())
    }

    fun containsAnyUser(): Either.Right<Boolean> {
        val id = preferences.getLong(PREF_USER_ID, 0)
        return Either.Right(id != 0L)
    }
}

private fun SharedPreferences.Editor.putSafely(key: String, value: Long?) {
    if (value != null && value != 0L) {
        putLong(key, value)
    }
}

private fun SharedPreferences.Editor.putSafely(key: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        putString(key, value)
    }
}