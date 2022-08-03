package com.example.chat.domain.messages

import androidx.room.TypeConverter

class ContactConverter {

    @TypeConverter
    fun toString(contact: ContactEntity?): String? {
        return if (contact == null) {
            null
        } else {
            "${contact.id}||${contact.name}||${contact.image}"
        }
    }

    fun toContact(string: String?): ContactEntity? {
        return if (string == null) {
            return null
        } else {
            val arr = string.split("||")
            ContactEntity(
                id = arr[0].toLong(),
                name = arr[1],
                image = arr[2]
            )
        }
    }
}