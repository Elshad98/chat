package com.example.chat.data.repository.friend

import com.example.chat.domain.friend.Friend
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface FriendRemote {

    fun getFriends(userId: Long, token: String): Either<Failure, List<Friend>>

    fun addFriend(email: String, requestUserId: Long, token: String): Either<Failure, None>

    fun getFriendRequests(userId: Long, token: String): Either<Failure, List<Friend>>

    fun approveFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None>

    fun cancelFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None>

    fun deleteFriend(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None>
}