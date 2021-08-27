package com.example.chat.domain.friends

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface FriendsRepository {

    fun getFriends(): Either<Failure, List<FriendEntity>>

    fun getFriendRequest(): Either<Failure, List<FriendEntity>>

    fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun addFriend(email: String): Either<Failure, None>

    fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None>
}