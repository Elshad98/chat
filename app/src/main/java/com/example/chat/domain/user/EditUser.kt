package com.example.chat.domain.user

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class EditUser(
    private val userRepository: UserRepository
) : UseCase<User, EditUser.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> {
        return userRepository.editUser(params.user)
    }

    data class Params(
        val user: User
    )
}