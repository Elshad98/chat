package com.example.chat.cache

import android.content.SharedPreferences
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val prefs: SharedPreferences
) {

    companion object {

        private const val ACCOUNT_ID = "account_id"
        private const val ACCOUNT_NAME = "account_name"
        private const val ACCOUNT_DATA = "account_data"
        private const val ACCOUNT_EMAIL = "account_email"
        private const val ACCOUNT_IMAGE = "account_image"
        private const val ACCOUNT_TOKEN = "account_token"
        private const val ACCOUNT_STATUS = "account_status"
    }

    fun getToken(): Either<Failure, String> {
        return Either.Right(prefs.getString(ACCOUNT_TOKEN, "")!!)
    }

    fun saveToken(token: String): Either<Failure, None> {
        prefs.edit().apply {
            putString(ACCOUNT_TOKEN, token)
        }.apply()

        return Either.Right(None())
    }

    fun saveAccount(account: AccountEntity): Either<Failure, None> {
        prefs.edit().apply {
            putLong(ACCOUNT_ID, account.id)
            putString(ACCOUNT_NAME, account.name)
            putLong(ACCOUNT_DATA, account.userData)
            putString(ACCOUNT_EMAIL, account.email)
            putString(ACCOUNT_TOKEN, account.token)
            putString(ACCOUNT_IMAGE, account.image)
            putString(ACCOUNT_STATUS, account.status)
        }.apply()

        return Either.Right(None())
    }

    fun getAccount(): Either<Failure, AccountEntity> {
        val id = prefs.getLong(ACCOUNT_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }

        val account = AccountEntity(
            id = prefs.getLong(ACCOUNT_ID, 0),
            userData = prefs.getLong(ACCOUNT_DATA, 0),
            name = prefs.getString(ACCOUNT_NAME, "")!!,
            email = prefs.getString(ACCOUNT_EMAIL, "")!!,
            image = prefs.getString(ACCOUNT_IMAGE, "")!!,
            token = prefs.getString(ACCOUNT_TOKEN, "")!!,
            status = prefs.getString(ACCOUNT_STATUS, "")!!
        )

        return Either.Right(account)
    }

    fun removeAccount(): Either<Failure, None> {
        prefs.edit().apply {
            remove(ACCOUNT_ID)
            remove(ACCOUNT_NAME)
            remove(ACCOUNT_DATA)
            remove(ACCOUNT_EMAIL)
            remove(ACCOUNT_IMAGE)
            remove(ACCOUNT_STATUS)
        }.apply()

        return Either.Right(None())
    }

    fun containsAnyAccount(): Boolean {
        val id = prefs.getLong(ACCOUNT_ID, 0)
        return id != 0L
    }
}