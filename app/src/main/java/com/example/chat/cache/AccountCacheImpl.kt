package com.example.chat.cache

import com.example.chat.data.account.AccountCache
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(
    private val prefsManager: SharedPrefsManager
) : AccountCache {

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }
}