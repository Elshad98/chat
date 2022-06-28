package com.example.chat.data.account

import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface AccountRemote {

    fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None>

    fun login(email: String, password: String, token: String): Either<Failure, AccountEntity>

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
    ): Either<Failure, AccountEntity>
}