package com.example.chat.domain.message

import java.util.Date

data class Message(
    val id: Long,
    val senderId: Long,
    val createdAt: Date,
    val fromMe: Boolean,
    val message: String,
    val contact: Contact,
    val receiverId: Long,
    val type: MessageType,
    val deletedBySender: Int,
    val deletedByReceiver: Int
)