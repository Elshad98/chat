package com.example.chat.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.local.model.UserEntity
import toothpick.InjectConstructor

@InjectConstructor
class UserStorage(context: Context) {

    companion object {

        private const val PREFERENCES_NAME = "user_store"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_IMAGE = "image"
        private const val KEY_TOKEN = "token"
        private const val KEY_STATUS = "status"
        private const val KEY_PASSWORD = "password"
        private const val KEY_CREATE_AT = "create_at"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getToken(): Either.Right<String> {
        return Either.Right(preferences.getString(KEY_TOKEN, "")!!)
    }

    fun saveToken(token: String): Either.Right<None> {
        preferences.edit {
            putString(KEY_TOKEN, token)
        }
        return Either.Right(None())
    }

    fun saveUser(user: UserEntity): Either.Right<None> {
        preferences.edit {
            putSafely(KEY_ID, user.id)
            putSafely(KEY_NAME, user.name)
            putSafely(KEY_EMAIL, user.email)
            putSafely(KEY_TOKEN, user.token)
            putSafely(KEY_IMAGE, user.image)
            putString(KEY_STATUS, user.status)
            putSafely(KEY_PASSWORD, user.password)
            putSafely(KEY_CREATE_AT, user.createdAt)
        }
        return Either.Right(None())
    }

    fun getUser(): Either<Failure, UserEntity> {
        val id = preferences.getLong(KEY_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedUsersError)
        }

        val user = UserEntity(
            id = id,
            name = preferences.getString(KEY_NAME, "")!!,
            email = preferences.getString(KEY_EMAIL, "")!!,
            image = preferences.getString(KEY_IMAGE, "")!!,
            token = preferences.getString(KEY_TOKEN, "")!!,
            status = preferences.getString(KEY_STATUS, "")!!,
            password = preferences.getString(KEY_PASSWORD, "")!!,
            createdAt = preferences.getLong(KEY_CREATE_AT, 0)
        )
        return Either.Right(user)
    }

    fun clear(): Either.Right<None> {
        preferences.edit {
            clear()
        }
        return Either.Right(None())
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