package com.example.chat.data.remote.model.dto

import com.example.chat.domain.friend.Friend
import com.google.gson.annotations.SerializedName

data class FriendDto(
    @SerializedName("user_id")
    val id: Long,
    val name: String,
    val email: String,
    val image: String,
    val status: String,
    val isRequest: Int,
    @SerializedName("last_seen")
    val lastSeen: Long,
    @SerializedName("friends_id")
    val friendsId: Long
)

fun FriendDto.toFriend() = Friend(
    id = id,
    name = name,
    email = email,
    image = image,
    status = status,
    lastSeen = lastSeen,
    isRequest = isRequest,
    friendsId = friendsId
)