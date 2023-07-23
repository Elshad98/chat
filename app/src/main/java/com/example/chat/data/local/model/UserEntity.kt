package com.example.chat.data.local.model

import com.example.chat.domain.user.User

data class UserEntity(
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val image: String,
    val status: String,
    val createdAt: Long,
    val password: String
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    status = status,
    password = password,
    createdAt = createdAt
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    status = status,
    password = password,
    createdAt = createdAt
)