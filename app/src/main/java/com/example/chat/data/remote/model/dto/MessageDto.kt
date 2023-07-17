package com.example.chat.data.remote.model.dto

import com.example.chat.domain.message.Message
import com.google.gson.annotations.SerializedName
import java.util.Date

data class MessageDto(
    @SerializedName("message_id")
    val id: Long,
    @SerializedName("message_date")
    val date: Date,
    @SerializedName("message_type_id")
    val type: MessageTypeDto,
    @SerializedName("from_me")
    val fromMe: Boolean,
    val message: String,
    @SerializedName("sender_id")
    val senderId: Long,
    @SerializedName("receiver_id")
    val receiverId: Long,
    val contact: ContactDto,
    @SerializedName("deleted_by_sender_id")
    val deletedBySender: Int,
    @SerializedName("deleted_by_receiver_id")
    val deletedByReceiver: Int
)

fun MessageDto.toDomain() = Message(
    id = id,
    date = date,
    fromMe = fromMe,
    message = message,
    senderId = senderId,
    type = type.toDomain(),
    receiverId = receiverId,
    contact = contact.toDomain(),
    deletedBySender = deletedBySender,
    deletedByReceiver = deletedByReceiver
)