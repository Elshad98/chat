package com.example.chat.domain.user

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class UpdateToken @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        userRepository.updateToken(params.token)

    data class Params(
        val token: String
    )
}