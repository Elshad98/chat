package com.example.chat.data.cache.model

import com.example.chat.domain.user.User

data class UserModel(
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val image: String,
    val status: String,
    val userData: Long,
    val password: String
)

fun UserModel.toUser() = User(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    status = status,
    userData = userData,
    password = password
)

fun User.toUserModel() = UserModel(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    status = status,
    userData = userData,
    password = password
)