package com.example.chat.domain.user

import com.example.chat.domain.interactor.UseCase
import com.example.chat.core.functional.Either
import com.example.chat.core.exception.Failure
import com.example.chat.core.None
import javax.inject.Inject

class CheckAuth @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Boolean, None>() {

    override suspend fun run(params: None): Either<Failure, Boolean> {
        return userRepository.checkAuth()
    }
}