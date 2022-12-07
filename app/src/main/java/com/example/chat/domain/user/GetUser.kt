package com.example.chat.domain.user

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import javax.inject.Inject

class GetUser @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<User, None>() {

    override suspend fun run(params: None): Either<Failure, User> {
        return userRepository.getUser()
    }
}