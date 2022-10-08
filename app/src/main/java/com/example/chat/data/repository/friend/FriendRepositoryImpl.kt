package com.example.chat.data.repository.friend

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onNext
import com.example.chat.data.remote.FriendRemoteDataSource
import com.example.chat.data.remote.model.dto.FriendDto
import com.example.chat.data.remote.model.dto.toFriend
import com.example.chat.data.repository.user.UserCache
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendCache: FriendCache,
    private val userCache: UserCache,
    private val friendRemoteDataSource: FriendRemoteDataSource
) : FriendRepository {

    override fun getFriends(needFetch: Boolean): Either<Failure, List<Friend>> {
        return userCache
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    friendRemoteDataSource
                        .getFriends(user.id, user.token)
                        .map { response -> response.friends.map(FriendDto::toFriend) }
                } else {
                    Either.Right(friendCache.getFriends())
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onNext { friends -> friends.map(friendCache::saveFriend) }
    }

    override fun getFriendRequest(needFetch: Boolean): Either<Failure, List<Friend>> {
        return userCache
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    friendRemoteDataSource
                        .getFriendRequests(user.id, user.token)
                        .map { response -> response.friendsRequests.map(FriendDto::toFriend) }
                } else {
                    Either.Right(friendCache.getFriendRequests())
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onNext { friends ->
                friends.map { friend ->
                    friend.isRequest = 1
                    friendCache.saveFriend(friend)
                }
            }
    }

    override fun approveFriendRequest(friend: Friend): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .approveFriendRequest(user.id, friend.id, friend.friendsId, user.token)
                    .map { None() }
            }
            .onNext {
                friend.isRequest = 0
                friendCache.saveFriend(friend)
            }
    }

    override fun cancelFriendRequest(friend: Friend): Either<Failure, None> {
        return userCache
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
            .onNext { friendCache.deleteFriend(friend.id) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                friendRemoteDataSource
                    .addFriend(email, user.id, user.token)
                    .map { None() }
            }
    }

    override fun deleteFriend(friend: Friend): Either<Failure, None> {
        return userCache
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
            .onNext { friendCache.deleteFriend(friend.id) }
    }
}