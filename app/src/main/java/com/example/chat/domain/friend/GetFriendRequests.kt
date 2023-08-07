package com.example.chat.domain.friend

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetFriendRequests(
    private val friendRepository: FriendRepository
) : UseCase<List<Friend>, None>() {

    override suspend fun run(params: None): Either<Failure, List<Friend>> {
        return friendRepository.getFriendRequests()
    }
}