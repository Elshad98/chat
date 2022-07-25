package com.example.chat.domain.account

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface AccountRepository {

    fun logout(): Either<Failure, None>

    fun checkAuth(): Either<Failure, Boolean>

    fun updateAccountLastSeen(): Either<Failure, None>

    fun getCurrentAccount(): Either<Failure, AccountEntity>

    fun forgetPassword(email: String): Either<Failure, None>

    fun updateAccountToken(token: String): Either<Failure, None>

    fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity>

    fun login(email: String, password: String): Either<Failure, AccountEntity>

    fun register(email: String, name: String, password: String): Either<Failure, None>
}