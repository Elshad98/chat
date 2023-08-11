package com.example.chat.domain.friend

import androidx.lifecycle.LiveData
import toothpick.InjectConstructor

@InjectConstructor
class GetLiveFriends(
    private val friendRepository: FriendRepository
) {

    operator fun invoke(): LiveData<List<Friend>> {
        return friendRepository.getLiveFriends()
    }
}