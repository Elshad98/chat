package com.example.chat.domain.friend

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import javax.inject.Inject

class DeleteFriend @Inject constructor(
    private val friendRepository: FriendRepository
) : UseCase<None, Friend>() {

    override suspend fun run(params: Friend): Either<Failure, None> {
        return friendRepository.deleteFriend(params)
    }
}