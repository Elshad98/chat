package com.example.chat.data.remote

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.remote.common.ApiParamBuilder
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.FriendRequestsResponse
import com.example.chat.data.remote.model.response.FriendsResponse
import com.example.chat.data.remote.service.FriendService
import toothpick.InjectConstructor

@InjectConstructor
class FriendRemoteDataSource(
    private val request: Request,
    private val service: FriendService
) {

    fun getFriends(userId: Long, token: String): Either<Failure, FriendsResponse> {
        val params = ApiParamBuilder()
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getFriends(params))
    }

    fun getFriendRequests(userId: Long, token: String): Either<Failure, FriendRequestsResponse> {
        val params = ApiParamBuilder()
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getFriendRequests(params))
    }

    fun addFriend(
        email: String,
        requestUserId: Long,
        token: String
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .email(email)
            .token(token)
            .build()
        return request.make(service.addFriend(params))
    }

    fun approveFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .friendsId(friendsId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.approveFriendRequest(params))
    }

    fun cancelFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .friendsId(friendsId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.cancelFriendRequest(params))
    }

    fun deleteFriend(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .requestUserId(requestUserId)
            .friendsId(friendsId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.deleteFriend(params))
    }
}