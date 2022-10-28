package com.example.chat.domain.message

data class Message(
    val id: Long,
    val type: Int,
    val date: Long,
    val senderId: Long,
    val fromMe: Boolean,
    val message: String,
    val contact: Contact,
    val receiverId: Long,
    val deletedBySender: Int,
    val deletedByReceiver: Int
)