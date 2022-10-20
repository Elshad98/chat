package com.example.chat.data.remote.model.dto

import com.example.chat.domain.message.Message
import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("message_id")
    val id: Long,
    @SerializedName("message_date")
    val date: Long,
    @SerializedName("message_type_id")
    val type: Int,
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
    type = type,
    fromMe = fromMe,
    message = message,
    senderId = senderId,
    receiverId = receiverId,
    contact = contact.toDomain(),
    deletedBySender = deletedBySender,
    deletedByReceiver = deletedByReceiver
)