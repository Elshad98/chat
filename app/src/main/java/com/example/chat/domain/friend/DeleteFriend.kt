package com.example.chat.domain.friend

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class DeleteFriend(
    private val friendRepository: FriendRepository
) : UseCase<None, DeleteFriend.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return friendRepository.deleteFriend(params.friend)
    }

    data class Params(
        val friend: Friend
    )
}