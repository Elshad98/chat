package com.example.chat.data.cache

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.map
import com.example.chat.data.cache.model.UserModel
import com.example.chat.data.cache.model.toUser
import com.example.chat.data.cache.model.toUserModel
import com.example.chat.data.repository.user.UserCache
import com.example.chat.domain.user.User
import javax.inject.Inject

class UserCacheImpl @Inject constructor(
    private val prefsManager: SharedPrefsManager,
    private val appDatabase: AppDatabase
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
        return prefsManager
            .getUser()
            .map(UserModel::toUser)
    }

    override fun saveUser(user: User): Either<Failure, None> {
        return user
            .toUserModel()
            .let(prefsManager::saveUser)
    }

    override fun checkAuth(): Either<Failure, Boolean> {
        return prefsManager.containsAnyUser()
    }
}