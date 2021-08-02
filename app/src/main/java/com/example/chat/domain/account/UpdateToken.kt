package com.example.chat.domain.account

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.exception.Failure
import javax.inject.Inject

class UpdateToken @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        accountRepository.updateAccountToken(params.token)

    data class Params(
        val token: String
    )
}