package com.example.chat.domain.friend

import com.example.chat.core.functional.Either
import com.example.chat.core.exception.Failure
import com.example.chat.core.None

interface FriendRepository {

    fun addFriend(email: String): Either<Failure, None>

    fun deleteFriend(friend: Friend): Either<Failure, None>

    fun cancelFriendRequest(friend: Friend): Either<Failure, None>

    fun approveFriendRequest(friend: Friend): Either<Failure, None>

    fun getFriends(needFetch: Boolean): Either<Failure, List<Friend>>

    fun getFriendRequest(needFetch: Boolean): Either<Failure, List<Friend>>
}