package com.example.chat.domain.user

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val status: String,
    val userData: Long,
    val image: String,
    val password: String
)