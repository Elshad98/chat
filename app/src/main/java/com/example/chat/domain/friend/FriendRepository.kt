package com.example.chat.domain.friend

import androidx.lifecycle.LiveData
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either

interface FriendRepository {

    fun getLiveFriends(): LiveData<List<Friend>>

    fun getFriends(): Either<Failure, List<Friend>>

    fun addFriend(email: String): Either<Failure, None>

    fun getFriendRequests(): Either<Failure, List<Friend>>

    fun deleteFriend(friend: Friend): Either<Failure, None>

    fun cancelFriendRequest(friend: Friend): Either<Failure, None>

    fun approveFriendRequest(friend: Friend): Either<Failure, None>
}