package com.example.chat.data.repository

import androidx.lifecycle.LiveData
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.map
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onSuccess
import com.example.chat.data.local.UserStorage
import com.example.chat.data.local.dao.FriendDao
import com.example.chat.data.local.model.FriendEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.data.local.model.toEntity
import com.example.chat.data.remote.common.ApiParamBuilder
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.model.dto.FriendDto
import com.example.chat.data.remote.model.dto.toDomain
import com.example.chat.data.remote.service.FriendService
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.FriendRepository
import toothpick.InjectConstructor

@InjectConstructor
class FriendRepositoryImpl(
    private val request: Request,
    private val friendDao: FriendDao,
    private val userStorage: UserStorage,
    private val friendService: FriendService
) : FriendRepository {

    override fun getFriends(): Either<Failure, List<Friend>> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(friendService.getFriends(params))
            }
            .map { response ->
                response.friends.map(FriendDto::toDomain)
            }
            .onSuccess { friends ->
                friendDao.saveFriends(friends.map(Friend::toEntity))
            }
    }

    override fun getLiveFriends(): LiveData<List<Friend>> {
        return friendDao
            .getLiveFriends()
            .map { friends ->
                friends.map(FriendEntity::toDomain)
            }
    }

    override fun getFriendRequests(): Either<Failure, List<Friend>> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(friendService.getFriendRequests(params))
            }
            .map { response ->
                response.friendsRequests.map(FriendDto::toDomain)
            }
    }

    override fun approveFriendRequest(friend: Friend): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .friendsId(friend.friendsId)
                    .requestUserId(friend.id)
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(friendService.approveFriendRequest(params))
            }
            .map { None() }
            .onSuccess {
                friendDao.saveFriend(friend.copy(isRequest = 0).toEntity())
            }
    }

    override fun cancelFriendRequest(friend: Friend): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .friendsId(friend.friendsId)
                    .requestUserId(friend.id)
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(friendService.cancelFriendRequest(params))
            }
            .map { None() }
            .onSuccess {
                friendDao.deleteFriend(friend.id)
            }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .requestUserId(user.id)
                    .token(user.token)
                    .email(email)
                    .build()
                request.make(friendService.addFriend(params))
            }
            .map { None() }
    }

    override fun deleteFriend(friend: Friend): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .friendsId(friend.friendsId)
                    .requestUserId(friend.id)
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(friendService.deleteFriend(params))
            }
            .map { None() }
            .onSuccess {
                friendDao.deleteFriend(friend.id)
            }
    }
}