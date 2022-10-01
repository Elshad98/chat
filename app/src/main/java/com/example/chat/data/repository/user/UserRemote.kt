package com.example.chat.data.repository.user

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.user.User

interface UserRemote {

    fun forgetPassword(email: String): Either<Failure, None>

    fun login(email: String, password: String, token: String): Either<Failure, User>

    fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None>

    fun updateUserLastSeen(userId: Long, token: String, lastSeen: Long): Either<Failure, None>

    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>

    fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Either<Failure, User>
}