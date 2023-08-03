package com.example.chat.domain.user

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class UpdateToken(
    private val userRepository: UserRepository
) : UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        userRepository.updateToken(params.token)

    data class Params(
        val token: String
    )
}