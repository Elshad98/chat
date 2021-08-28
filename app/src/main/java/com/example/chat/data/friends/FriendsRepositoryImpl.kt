package com.example.chat.data.friends

import com.example.chat.data.account.AccountCache
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.friends.FriendsRepository
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.type.flatMap

class FriendsRepositoryImpl(
    private val accountCache: AccountCache,
    private val friendsRemote: FriendsRemote
) : FriendsRepository {

    override fun getFriends(): Either<Failure, List<FriendEntity>> {
        return accountCache
            .getCurrentAccount()
            .flatMap { accountEntity ->
                friendsRemote.getFriends(accountEntity.id, accountEntity.token)
            }
    }

    override fun getFriendRequest(): Either<Failure, List<FriendEntity>> {
        return accountCache
            .getCurrentAccount()
            .flatMap { accountEntity ->
                friendsRemote.getFriendRequests(accountEntity.id, accountEntity.token)
            }
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { accountEntity ->
                friendsRemote.approveFriendRequest(
                    accountEntity.id,
                    friendEntity.id,
                    friendEntity.friendsId,
                    accountEntity.token
                )
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
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { accountEntity ->
                friendsRemote.addFriend(email, accountEntity.id, accountEntity.token)
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
    }
}