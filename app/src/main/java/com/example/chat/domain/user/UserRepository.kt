package com.example.chat.domain.user

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface UserRepository {

    fun logout(): Either<Failure, None>

    fun getUser(): Either<Failure, User>

    fun checkAuth(): Either<Failure, Boolean>

    fun updateUserLastSeen(): Either<Failure, None>

    fun editUser(user: User): Either<Failure, User>

    fun updateToken(token: String): Either<Failure, None>

    fun forgetPassword(email: String): Either<Failure, None>

    fun login(email: String, password: String): Either<Failure, User>

    fun register(email: String, name: String, password: String): Either<Failure, None>
}