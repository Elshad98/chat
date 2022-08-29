package com.example.chat.domain.account

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class ForgetPassword @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, ForgetPassword.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return accountRepository.forgetPassword(params.email)
    }

    data class Params(val email: String)
}