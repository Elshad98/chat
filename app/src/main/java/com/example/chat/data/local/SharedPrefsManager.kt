package com.example.chat.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.local.model.UserEntity
import toothpick.InjectConstructor

@InjectConstructor
class SharedPrefsManager(
    private val preferences: SharedPreferences
) {

    companion object {

        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_IMAGE = "user_image"
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_USER_STATUS = "user_status"
        private const val KEY_USER_PASSWORD = "user_password"
        private const val KEY_USER_CREATE_AT = "user_create_at"
    }

    fun getToken(): Either.Right<String> {
        return Either.Right(preferences.getString(KEY_USER_TOKEN, "")!!)
    }

    fun saveToken(token: String): Either.Right<None> {
        preferences.edit {
            putString(KEY_USER_TOKEN, token)
        }
        return Either.Right(None())
    }

    fun saveUser(user: UserEntity): Either.Right<None> {
        preferences.edit {
            putSafely(KEY_USER_ID, user.id)
            putSafely(KEY_USER_NAME, user.name)
            putSafely(KEY_USER_EMAIL, user.email)
            putSafely(KEY_USER_TOKEN, user.token)
            putSafely(KEY_USER_IMAGE, user.image)
            putString(KEY_USER_STATUS, user.status)
            putSafely(KEY_USER_PASSWORD, user.password)
            putSafely(KEY_USER_CREATE_AT, user.createdAt)
        }
        return Either.Right(None())
    }

    fun getUser(): Either<Failure, UserEntity> {
        val id = preferences.getLong(KEY_USER_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }

        val user = UserEntity(
            id = id,
            name = preferences.getString(KEY_USER_NAME, "")!!,
            email = preferences.getString(KEY_USER_EMAIL, "")!!,
            image = preferences.getString(KEY_USER_IMAGE, "")!!,
            token = preferences.getString(KEY_USER_TOKEN, "")!!,
            status = preferences.getString(KEY_USER_STATUS, "")!!,
            password = preferences.getString(KEY_USER_PASSWORD, "")!!,
            createdAt = preferences.getLong(KEY_USER_CREATE_AT, 0)
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
        val id = preferences.getLong(KEY_USER_ID, 0)
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