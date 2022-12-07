package com.example.chat.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chat.domain.message.Message

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: Long,
    val type: Int,
    val date: Long,
    @ColumnInfo(name = "from_me")
    val fromMe: Boolean,
    val message: String,
    @ColumnInfo(name = "sender_id")
    val senderId: Long,
    @ColumnInfo(name = "receiver_id")
    val receiverId: Long,
    @ColumnInfo(name = "deleted_by_sender_id")
    val deletedBySender: Int,
    @ColumnInfo(name = "deleted_by_receiver_id")
    val deletedByReceiver: Int,
    val contact: ContactEntity
)

fun Message.toEntity() = MessageEntity(
    id = id,
    date = date,
    type = type,
    fromMe = fromMe,
    message = message,
    senderId = senderId,
    receiverId = receiverId,
    contact = contact.toEntity(),
    deletedBySender = deletedBySender,
    deletedByReceiver = deletedByReceiver
)

fun MessageEntity.toDomain() = Message(
    id = id,
    date = date,
    type = type,
    fromMe = fromMe,
    message = message,
    senderId = senderId,
    receiverId = receiverId,
    contact = contact.toDomain(),
    deletedBySender = deletedBySender,
    deletedByReceiver = deletedByReceiver
)