package com.example.chat.cache

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val preferences: SharedPreferences
) {

    companion object {

        private const val PREF_ACCOUNT_ID = "account_id"
        private const val PREF_ACCOUNT_NAME = "account_name"
        private const val PREF_ACCOUNT_DATA = "account_data"
        private const val PREF_ACCOUNT_EMAIL = "account_email"
        private const val PREF_ACCOUNT_IMAGE = "account_image"
        private const val PREF_ACCOUNT_TOKEN = "account_token"
        private const val PREF_ACCOUNT_STATUS = "account_status"
        private const val PREF_ACCOUNT_PASSWORD = "account_password"
    }

    fun getToken(): Either<Failure, String> {
        return Either.Right(preferences.getString(PREF_ACCOUNT_TOKEN, "")!!)
    }

    fun saveToken(token: String): Either<Failure, None> {
        preferences.edit {
            putString(PREF_ACCOUNT_TOKEN, token)
        }

        return Either.Right(None())
    }

    fun saveAccount(account: AccountEntity): Either<Failure, None> {
        preferences.edit {
            putSafely(PREF_ACCOUNT_ID, account.id)
            putSafely(PREF_ACCOUNT_NAME, account.name)
            putSafely(PREF_ACCOUNT_EMAIL, account.email)
            putSafely(PREF_ACCOUNT_TOKEN, account.token)
            putSafely(PREF_ACCOUNT_IMAGE, account.image)
            putString(PREF_ACCOUNT_STATUS, account.status)
            putSafely(PREF_ACCOUNT_DATA, account.userData)
            putSafely(PREF_ACCOUNT_PASSWORD, account.password)
        }

        return Either.Right(None())
    }

    fun getAccount(): Either<Failure, AccountEntity> {
        val id = preferences.getLong(PREF_ACCOUNT_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }

        val account = AccountEntity(
            id = id,
            userData = preferences.getLong(PREF_ACCOUNT_DATA, 0),
            name = preferences.getString(PREF_ACCOUNT_NAME, "")!!,
            email = preferences.getString(PREF_ACCOUNT_EMAIL, "")!!,
            image = preferences.getString(PREF_ACCOUNT_IMAGE, "")!!,
            token = preferences.getString(PREF_ACCOUNT_TOKEN, "")!!,
            status = preferences.getString(PREF_ACCOUNT_STATUS, "")!!,
            password = preferences.getString(PREF_ACCOUNT_PASSWORD, "")!!
        )
        return Either.Right(account)
    }

    fun removeAccount(): Either<Failure, None> {
        preferences.edit {
            remove(PREF_ACCOUNT_ID)
            remove(PREF_ACCOUNT_NAME)
            remove(PREF_ACCOUNT_DATA)
            remove(PREF_ACCOUNT_EMAIL)
            remove(PREF_ACCOUNT_IMAGE)
            remove(PREF_ACCOUNT_STATUS)
            remove(PREF_ACCOUNT_PASSWORD)
        }

        return Either.Right(None())
    }

    fun containsAnyAccount(): Either<Failure, Boolean> {
        val id = preferences.getLong(PREF_ACCOUNT_ID, 0)
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