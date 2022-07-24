package com.example.chat.data.friends

import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface FriendsRemote {

    fun addFriend(email: String, userId: Long, token: String): Either<Failure, None>

    fun getFriends(userId: Long, token: String): Either<Failure, List<FriendEntity>>

    fun getFriendRequests(userId: Long, token: String): Either<Failure, List<FriendEntity>>

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