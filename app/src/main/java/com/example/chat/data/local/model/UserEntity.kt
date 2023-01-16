package com.example.chat.data.local.model

import com.example.chat.domain.user.User

data class UserEntity(
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val image: String,
    val photo: String,
    val status: String,
    val userData: Long,
    val password: String
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    photo = photo,
    status = status,
    userData = userData,
    password = password
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    photo = photo,
    status = status,
    userData = userData,
    password = password
)