package com.example.chat.domain.friends

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class AddFriend @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<None, AddFriend.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return friendsRepository.addFriend(params.email)
    }

    data class Params(
        val email: String
    )
}