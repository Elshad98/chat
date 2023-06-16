package com.example.chat.domain.friend

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import javax.inject.Inject

class GetFriendById @Inject constructor(
    private val friendRepository: FriendRepository
) : UseCase<Friend?, Long>() {

    override suspend fun run(id: Long): Either<Failure, Friend?> {
        return friendRepository.getFriendById(id)
    }
}