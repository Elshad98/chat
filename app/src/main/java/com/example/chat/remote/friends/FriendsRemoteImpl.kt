package com.example.chat.remote.friends

import com.example.chat.data.friends.FriendsRemote
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.remote.core.Request
import com.example.chat.remote.service.ApiService
import javax.inject.Inject

class FriendsRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: ApiService
) : FriendsRemote {

    override fun getFriends(userId: Long, token: String): Either<Failure, List<FriendEntity>> {
        val params = createGetFriendsMap(userId, token)
        return request.make(service.getFriends(params)) { it.friends }
    }

    override fun getFriendRequests(
        userId: Long,
        token: String
    ): Either<Failure, List<FriendEntity>> {
        val params = createGetFriendRequestsMap(userId, token)
        return request.make(service.getFriendRequests(params)) { it.friendsRequests }
    }

    override fun approveFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None> {
        val params = createApproveFriendRequestMap(userId, requestUserId, friendsId, token)
        return request.make(service.approveFriendRequest(params)) { None() }
    }

    override fun cancelFriendRequest(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None> {
        val params = createCancelFriendRequestMap(userId, requestUserId, friendsId, token)
        return request.make(service.cancelFriendRequest(params)) { None() }
    }

    override fun addFriend(email: String, userId: Long, token: String): Either<Failure, None> {
        val params = createAddFriendMap(email, userId, token)
        return request.make(service.addFriend(params)) { None() }
    }

    override fun deleteFriend(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Either<Failure, None> {
        val params = createDeleteFriendMap(userId, requestUserId, friendsId, token)
        return request.make(service.deleteFriend(params)) { None() }
    }

    private fun createGetFriendsMap(userId: Long, token: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        return map
    }

    private fun createGetFriendRequestsMap(userId: Long, token: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        return map
    }

    private fun createApproveFriendRequestMap(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        map[ApiService.PARAM_FRIENDS_ID] = friendsId.toString()
        map[ApiService.PARAM_REQUEST_USER_ID] = requestUserId.toString()
        return map
    }

    private fun createCancelFriendRequestMap(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        map[ApiService.PARAM_FRIENDS_ID] = friendsId.toString()
        map[ApiService.PARAM_REQUEST_USER_ID] = requestUserId.toString()
        return map
    }

    private fun createAddFriendMap(
        email: String,
        userId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_EMAIL] = email
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_REQUEST_USER_ID] = userId.toString()
        return map
    }

    private fun createDeleteFriendMap(
        userId: Long,
        requestUserId: Long,
        friendsId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        map[ApiService.PARAM_FRIENDS_ID] = friendsId.toString()
        map[ApiService.PARAM_REQUEST_USER_ID] = requestUserId.toString()
        return map
    }
}