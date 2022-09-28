package com.example.chat.data.repository.friends

import com.example.chat.data.repository.account.AccountCache
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.friends.FriendsRepository
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.type.flatMap
import com.example.chat.domain.type.map
import com.example.chat.domain.type.onNext
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendsCache: FriendsCache,
    private val accountCache: AccountCache,
    private val friendsRemote: FriendsRemote
) : FriendsRepository {

    override fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                if (needFetch) {
                    friendsRemote.getFriends(account.id, account.token)
                } else {
                    Either.Right(friendsCache.getFriends())
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onNext { friends -> friends.map(friendsCache::saveFriend) }
    }

    override fun getFriendRequest(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                if (needFetch) {
                    friendsRemote.getFriendRequests(account.id, account.token)
                } else {
                    Either.Right(friendsCache.getFriendRequests())
                }
            }
            .map { friends -> friends.sortedBy { friend -> friend.name } }
            .onNext { friends ->
                friends.map { friend ->
                    friend.isRequest = 1
                    friendsCache.saveFriend(friend)
                }
            }
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                friendsRemote.approveFriendRequest(
                    account.id,
                    friendEntity.id,
                    friendEntity.friendsId,
                    account.token
                )
            }
            .onNext {
                friendEntity.isRequest = 0
                friendsCache.saveFriend(friendEntity)
            }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { accountEntity ->
                friendsRemote.cancelFriendRequest(
                    accountEntity.id,
                    friendEntity.id,
                    friendEntity.friendsId,
                    accountEntity.token
                )
            }
            .onNext { friendsCache.deleteFriend(friendEntity.id) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                friendsRemote.addFriend(email, account.id, account.token)
            }
    }

    override fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { accountEntity ->
                friendsRemote.deleteFriend(
                    accountEntity.id,
                    friendEntity.id,
                    friendEntity.friendsId,
                    accountEntity.token
                )
            }
            .onNext { friendsCache.deleteFriend(friendEntity.id) }
    }
}