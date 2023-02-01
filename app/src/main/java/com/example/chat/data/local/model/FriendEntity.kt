package com.example.chat.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chat.domain.friend.Friend

@Entity(tableName = "friends")
data class FriendEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val email: String,
    val image: String,
    val photo: String,
    val status: String,
    @ColumnInfo(name = "last_seen")
    val lastSeen: Long,
    @ColumnInfo(name = "is_request")
    val isRequest: Int,
    @ColumnInfo(name = "friends_id")
    val friendsId: Long
)

fun Friend.toEntity() = FriendEntity(
    id = id,
    name = name,
    email = email,
    image = image,
    photo = photo,
    status = status,
    lastSeen = lastSeen,
    isRequest = isRequest,
    friendsId = friendsId
)

fun FriendEntity.toDomain() = Friend(
    id = id,
    name = name,
    email = email,
    image = image,
    photo = photo,
    status = status,
    lastSeen = lastSeen,
    isRequest = isRequest,
    friendsId = friendsId
)