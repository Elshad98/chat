package com.example.chat.data.local

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.local.model.UserEntity
import toothpick.InjectConstructor

@InjectConstructor
class UserLocalDataSource(
    private val chatDatabase: ChatDatabase,
    private val prefsManager: SharedPrefsManager
) {

    fun getToken(): Either<Failure, String> = prefsManager.getToken()

    fun getUser(): Either<Failure, UserEntity> = prefsManager.getUser()

    fun checkAuth(): Either<Failure, Boolean> = prefsManager.containsAnyUser()

    fun saveToken(token: String): Either<Failure, None> = prefsManager.saveToken(token)

    fun saveUser(user: UserEntity): Either<Failure, None> = prefsManager.saveUser(user)

    fun logout(): Either<Failure, None> {
        chatDatabase.clearAllTables()
        return prefsManager.clear()
    }
}