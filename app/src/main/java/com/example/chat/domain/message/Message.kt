package com.example.chat.domain.message

import java.util.Date

data class Message(
    val id: Long,
    val date: Date,
    val senderId: Long,
    val fromMe: Boolean,
    val message: String,
    val contact: Contact,
    val receiverId: Long,
    val type: MessageType,
    val deletedBySender: Int,
    val deletedByReceiver: Int
)