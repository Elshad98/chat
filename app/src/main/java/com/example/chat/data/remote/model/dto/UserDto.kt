package com.example.chat.data.remote.model.dto

import com.example.chat.domain.user.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id")
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val image: String,
    val status: String,
    @SerializedName("user_data")
    val createdAt: Long
)

fun UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email,
    token = token,
    image = image,
    status = status,
    createdAt = createdAt,
    password = ""
)