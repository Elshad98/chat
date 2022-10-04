package com.example.chat.data.repository.user

import com.example.chat.core.functional.Either
import com.example.chat.core.exception.Failure
import com.example.chat.core.None
import com.example.chat.domain.user.User

interface UserCache {

    fun logout(): Either<Failure, None>

    fun getUser(): Either<Failure, User>

    fun getToken(): Either<Failure, String>

    fun checkAuth(): Either<Failure, Boolean>

    fun saveUser(user: User): Either<Failure, None>

    fun saveToken(token: String): Either<Failure, None>
}