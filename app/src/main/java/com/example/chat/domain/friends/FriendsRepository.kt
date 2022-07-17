package com.example.chat.domain.friends

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface FriendsRepository {

    fun addFriend(email: String): Either<Failure, None>

    fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None>

    fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>>

    fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun getFriendRequest(needFetch: Boolean): Either<Failure, List<FriendEntity>>
}