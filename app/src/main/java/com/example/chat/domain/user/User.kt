package com.example.chat.domain.user

data class User(
    var id: Long,
    var name: String,
    var email: String,
    var token: String,
    var status: String,
    var userData: Long,
    var image: String,
    var password: String
)