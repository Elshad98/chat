package com.example.chat.domain.account

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.exception.Failure

interface AccountRepository {

    fun login(email: String, password: String): Either<Failure, AccountEntity>
    fun logout(): Either<Failure, None>
    fun register(email: String, name: String, password: String): Either<Failure, None>
    fun forgetPassword(email: String): Either<Failure, None>

    fun getCurrentAccount(): Either<Failure, AccountEntity>

    fun updateAccountToken(token: String): Either<Failure, None>
    fun updateAccountLastSeen(): Either<Failure, None>

    fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity>
}