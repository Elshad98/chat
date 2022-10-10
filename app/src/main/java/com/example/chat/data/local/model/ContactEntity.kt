package com.example.chat.data.local.model

import com.example.chat.domain.message.Contact

data class ContactEntity(
    val id: Long,
    val name: String,
    val image: String,
    val lastSeen: Long
)

fun Contact.toEntity() = ContactEntity(
    id = id,
    name = name,
    image = image,
    lastSeen = lastSeen
)

fun ContactEntity.toDomain() = Contact(
    id = id,
    name = name,
    image = image,
    lastSeen = lastSeen
)