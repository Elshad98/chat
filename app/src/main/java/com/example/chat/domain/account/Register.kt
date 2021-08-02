package com.example.chat.domain.account

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.exception.Failure
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: AccountRepository
) : UseCase<None, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        repository.register(params.email, params.name, params.password)

    data class Params(
        val email: String,
        val name: String,
        val password: String
    )
}