package com.example.chat.domain.message

data class Contact(
    val id: Long,
    val name: String,
    val image: String,
    val lastSeen: Long
)