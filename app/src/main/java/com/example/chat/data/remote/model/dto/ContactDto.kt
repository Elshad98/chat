package com.example.chat.data.remote.model.dto

import com.example.chat.domain.message.Contact
import com.google.gson.annotations.SerializedName

data class ContactDto(
    @SerializedName("user_id")
    val id: Long,
    val name: String,
    val image: String,
    @SerializedName("last_seen")
    val lastSeen: Long
)

fun ContactDto.toDomain() = Contact(
    id = id,
    name = name,
    image = image,
    lastSeen = lastSeen
)