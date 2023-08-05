package com.example.chat.domain.friend

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetFriends(
    private val friendRepository: FriendRepository
) : UseCase<List<Friend>, GetFriends.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Friend>> {
        return friendRepository.getFriends(params.needFetch)
    }

    data class Params(
        val needFetch: Boolean
    )
}