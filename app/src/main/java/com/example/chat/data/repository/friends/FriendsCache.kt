package com.example.chat.data.repository.friends

import com.example.chat.domain.friends.FriendEntity

interface FriendsCache {

    fun deleteFriend(id: Long)

    fun saveFriend(friend: FriendEntity)

    fun getFriends(): List<FriendEntity>

    fun getFriend(id: Long): FriendEntity?

    fun getFriendRequests(): List<FriendEntity>
}