package com.example.chat.data.account

import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface AccountCache {

    fun logout(): Either<Failure, None>

    fun getToken(): Either<Failure, String>

    fun checkAuth(): Either<Failure, Boolean>

    fun saveToken(token: String): Either<Failure, None>

    fun getCurrentAccount(): Either<Failure, AccountEntity>

    fun saveAccount(account: AccountEntity): Either<Failure, None>
}