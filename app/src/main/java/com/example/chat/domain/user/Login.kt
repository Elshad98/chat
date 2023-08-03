package com.example.chat.domain.user

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class Login(
    private val userRepository: UserRepository
) : UseCase<User, Login.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> {
        return userRepository.login(params.email, params.password)
    }

    data class Params(
        val email: String,
        val password: String
    )
}