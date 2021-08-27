package com.example.chat.domain.friends

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class ApproveFriendRequest @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<None, FriendEntity>() {

    override suspend fun run(params: FriendEntity): Either<Failure, None> {
        return friendsRepository.approveFriendRequest(params)
    }
}