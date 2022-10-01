package com.example.chat.domain.friend

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class AddFriend @Inject constructor(
    private val friendRepository: FriendRepository
) : UseCase<None, AddFriend.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return friendRepository.addFriend(params.email)
    }

    data class Params(
        val email: String
    )
}