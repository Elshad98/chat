package com.example.chat.domain.friend

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class DeleteFriend @Inject constructor(
    private val friendRepository: FriendRepository
) : UseCase<None, Friend>() {

    override suspend fun run(params: Friend): Either<Failure, None> {
        return friendRepository.deleteFriend(params)
    }
}