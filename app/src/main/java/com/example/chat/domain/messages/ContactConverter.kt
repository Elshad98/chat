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

    @TypeConverter
    fun toContact(string: String?): ContactEntity? {
        return if (string == null) {
            return null
        } else {
            val array = string.split("||")
            ContactEntity(
                id = array[0].toLong(),
                name = array[1],
                image = array[2]
            )
        }
    }
}