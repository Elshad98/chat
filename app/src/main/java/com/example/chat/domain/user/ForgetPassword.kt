package com.example.chat.domain.user

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class ForgetPassword(
    private val userRepository: UserRepository
) : UseCase<None, ForgetPassword.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return userRepository.forgetPassword(params.email)
    }

    data class Params(
        val email: String
    )
}