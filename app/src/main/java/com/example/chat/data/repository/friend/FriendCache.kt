package com.example.chat.data.repository.friend

import com.example.chat.domain.friend.Friend

interface FriendCache {

    fun deleteFriend(id: Long)

    fun saveFriend(friend: Friend)

    fun getFriends(): List<Friend>

    fun getFriend(id: Long): Friend?

    fun getFriendRequests(): List<Friend>
}