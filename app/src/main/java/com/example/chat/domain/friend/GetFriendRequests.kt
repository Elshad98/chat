package com.example.chat.domain.friend

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetFriendRequests(
    private val friendRepository: FriendRepository
) : UseCase<List<Friend>, Boolean>() {

    override suspend fun run(needFetch: Boolean): Either<Failure, List<Friend>> {
        return friendRepository.getFriendRequest(needFetch)
    }
}