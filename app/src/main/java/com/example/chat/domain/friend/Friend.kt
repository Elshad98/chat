package com.example.chat.domain.friend

data class Friend(
    val id: Long,
    val name: String,
    val email: String,
    val friendsId: Long,
    val status: String,
    val image: String,
    val isRequest: Int,
    val lastSeen: Long
)