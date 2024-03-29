package com.example.chat.domain.friend

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class AddFriend(
    private val friendRepository: FriendRepository
) : UseCase<None, AddFriend.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return friendRepository.addFriend(params.email)
    }

    data class Params(
        val email: String
    )
}