package com.example.chat.domain.friends

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class GetFriends @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, Boolean>() {

    override suspend fun run(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return friendsRepository.getFriends(needFetch)
    }
}