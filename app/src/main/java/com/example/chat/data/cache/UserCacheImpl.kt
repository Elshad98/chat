package com.example.chat.data.cache

import com.example.chat.data.repository.user.UserCache
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.user.User
import javax.inject.Inject

class UserCacheImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val prefsManager: SharedPrefsManager
) : UserCache {

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }

    override fun logout(): Either<Failure, None> {
        appDatabase.clearAllTables()
        return prefsManager.deleteUser()
    }

    override fun getUser(): Either<Failure, User> {
        return prefsManager.getUser()
    }

    override fun saveUser(user: User): Either<Failure, None> {
        return prefsManager.saveUser(user)
    }

    override fun checkAuth(): Either<Failure, Boolean> {
        return prefsManager.containsAnyUser()
    }
}