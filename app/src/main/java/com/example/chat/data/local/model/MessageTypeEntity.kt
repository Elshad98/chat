package com.example.chat.data.local.model

import com.example.chat.domain.message.MessageType

enum class MessageTypeEntity {
    TEXT,
    IMAGE
}

fun MessageType.toEntity() = MessageTypeEntity.valueOf(name)

fun MessageTypeEntity.toDomain() = MessageType.valueOf(name)