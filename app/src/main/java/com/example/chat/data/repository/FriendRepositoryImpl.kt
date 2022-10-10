package com.example.chat.data.repository

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onNext
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
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
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
                    Either.Right(friendLocalDataSource.getFriends().map(FriendEntity::toDomain))
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onNext { friends ->
                friends.forEach {
                    friendLocalDataSource.saveFriend(it.toEntity())
                }
            }
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
                    val friendRequests = friendLocalDataSource
                        .getFriendRequests()
                        .map(FriendEntity::toDomain)
                    Either.Right(friendRequests)
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onNext { friends ->
                friends.forEach { friend ->
                    friendLocalDataSource.saveFriend(friend.copy(isRequest = 1).toEntity())
                }
            }
    }

    override fun approveFriendRequest(friend: Friend): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .approveFriendRequest(user.id, friend.id, friend.friendsId, user.token)
                    .map { None() }
            }
            .onNext {
                friendLocalDataSource.saveFriend(friend.copy(isRequest = 0).toEntity())
            }
    }

    override fun cancelFriendRequest(friend: Friend): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .cancelFriendRequest(
                        user.id,
                        friend.id,
                        friend.friendsId,
                        user.token
                    )
                    .map { None() }
            }
            .onNext { friendLocalDataSource.deleteFriend(friend.id) }
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
                    .deleteFriend(
                        user.id,
                        friend.id,
                        friend.friendsId,
                        user.token
                    )
                    .map { None() }
            }
            .onNext { friendLocalDataSource.deleteFriend(friend.id) }
    }
}