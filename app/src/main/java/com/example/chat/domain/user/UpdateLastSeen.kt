package com.example.chat.domain.user

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class UpdateLastSeen(
    private val userRepository: UserRepository
) : UseCase<None, None>() {

    override suspend fun run(params: None): Either<Failure, None> {
        return userRepository.updateUserLastSeen()
    }
}