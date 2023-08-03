package com.example.chat.data.repository

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.Either.Right
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onSuccess
import com.example.chat.data.local.FriendLocalDataSource
import com.example.chat.data.local.UserLocalDataSource
import com.example.chat.data.local.model.FriendEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.data.local.model.toEntity
import com.example.chat.data.remote.FriendRemoteDataSource
import com.example.chat.data.remote.model.dto.FriendDto
import com.example.chat.data.remote.model.dto.toDomain
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.FriendRepository
import toothpick.InjectConstructor

@InjectConstructor
class FriendRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val friendLocalDataSource: FriendLocalDataSource,
    private val friendRemoteDataSource: FriendRemoteDataSource
) : FriendRepository {

    override fun getFriends(needFetch: Boolean): Either<Failure, List<Friend>> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    friendRemoteDataSource
                        .getFriends(user.id, user.token)
                        .map { response -> response.friends.map(FriendDto::toDomain) }
                } else {
                    friendLocalDataSource
                        .getFriends()
                        .map(FriendEntity::toDomain)
                        .let(::Right)
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onSuccess { friends -> friendLocalDataSource.saveFriends(friends.map(Friend::toEntity)) }
    }

    override fun getFriendById(id: Long): Either<Failure, Friend?> {
        return friendLocalDataSource
            .getFriendById(id)
            ?.let(FriendEntity::toDomain)
            .let(::Right)
    }

    override fun getFriendRequest(needFetch: Boolean): Either<Failure, List<Friend>> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    friendRemoteDataSource
                        .getFriendRequests(user.id, user.token)
                        .map { response -> response.friendsRequests.map(FriendDto::toDomain) }
                } else {
                    friendLocalDataSource
                        .getFriendRequests()
                        .map(FriendEntity::toDomain)
                        .let(::Right)
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
    }

    override fun approveFriendRequest(friend: Friend): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .approveFriendRequest(user.id, friend.id, friend.friendsId, user.token)
                    .map { None() }
            }
            .onSuccess { friendLocalDataSource.saveFriend(friend.copy(isRequest = 0).toEntity()) }
    }

    override fun cancelFriendRequest(friend: Friend): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .cancelFriendRequest(user.id, friend.id, friend.friendsId, user.token)
                    .map { None() }
            }
            .onSuccess { friendLocalDataSource.deleteFriend(friend.id) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .addFriend(email, user.id, user.token)
                    .map { None() }
            }
    }

    override fun deleteFriend(friend: Friend): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .deleteFriend(user.id, friend.id, friend.friendsId, user.token)
                    .map { None() }
            }
            .onSuccess { friendLocalDataSource.deleteFriend(friend.id) }
    }
}