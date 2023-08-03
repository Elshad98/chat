package com.example.chat.domain.user

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class Register(
    private val repository: UserRepository
) : UseCase<User, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> =
        repository.register(params.email, params.name, params.password)

    data class Params(
        val email: String,
        val name: String,
        val password: String
    )
}