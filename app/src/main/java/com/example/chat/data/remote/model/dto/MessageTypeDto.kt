package com.example.chat.data.remote.model.dto

import com.example.chat.domain.message.MessageType
import com.google.gson.annotations.SerializedName

enum class MessageTypeDto {
    @SerializedName("1")
    TEXT,

    @SerializedName("2")
    IMAGE
}

fun MessageTypeDto.toDomain() = MessageType.valueOf(name)