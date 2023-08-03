package com.example.chat.domain.user

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class EditUser(
    private val repository: UserRepository
) : UseCase<User, User>() {

    override suspend fun run(params: User): Either<Failure, User> {
        return repository.editUser(params)
    }
}