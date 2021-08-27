package com.example.chat.domain.friends

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, None>() {

    override suspend fun run(params: None): Either<Failure, List<FriendEntity>> {
        return friendsRepository.getFriendRequest()
    }
}