package com.example.chat.domain.account

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class Login @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, Login.Params>() {

    override suspend fun run(params: Params): Either<Failure, AccountEntity> {
        return accountRepository.login(params.email, params.password)
    }

    data class Params(
        val email: String,
        val password: String
    )
}