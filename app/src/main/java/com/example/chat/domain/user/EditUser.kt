package com.example.chat.domain.user

import com.example.chat.domain.interactor.UseCase
import com.example.chat.core.functional.Either
import com.example.chat.core.exception.Failure
import javax.inject.Inject

class EditUser @Inject constructor(
    private val repository: UserRepository
) : UseCase<User, User>() {

    override suspend fun run(params: User): Either<Failure, User> {
        return repository.editUser(params)
    }
}