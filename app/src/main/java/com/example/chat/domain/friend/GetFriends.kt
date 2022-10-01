package com.example.chat.domain.friend

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class GetFriends @Inject constructor(
    private val friendRepository: FriendRepository
) : UseCase<List<Friend>, Boolean>() {

    override suspend fun run(needFetch: Boolean): Either<Failure, List<Friend>> {
        return friendRepository.getFriends(needFetch)
    }
}