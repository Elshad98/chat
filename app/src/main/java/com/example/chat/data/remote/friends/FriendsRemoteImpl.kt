package com.example.chat.data.remote.friends

import com.example.chat.data.remote.core.ApiParamBuilder
import com.example.chat.data.remote.core.Request
import com.example.chat.data.remote.service.FriendsService
import com.example.chat.data.repository.friends.FriendsRemote
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class FriendsRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: FriendsService
) : FriendsRemote {

    override fun getFriends(userId: Long, token: String): Either<Failure, List<FriendEntity>> {
        val params = ApiParamBuilder()
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getFriends(params)) { it.friends }
    }

    override fun getFriendRequests(
        userId: Long,
        token: String
    ): Either<Failure, List<FriendEntity>> {
        val params = ApiParamBuilder()
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getFriendRequests(params)) { it.friendsRequests }
    }

    override fun approveFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .friendsId(friendsId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.approveFriendRequest(params)) { None() }
    }

    override fun cancelFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .friendsId(friendsId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.cancelFriendRequest(params)) { None() }
    }

    override fun addFriend(
        email: String,
        requestUserId: Long,
        token: String
    ): Either<Failure, None> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .email(email)
            .token(token)
            .build()
        return request.make(service.addFriend(params)) { None() }
    }

    override fun deleteFriend(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .friendsId(friendsId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.deleteFriend(params)) { None() }
    }
}