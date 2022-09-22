package com.example.chat.domain.messages

import androidx.room.TypeConverter
import com.google.gson.Gson

class ContactConverter {

    @TypeConverter
    fun contactToString(contact: ContactEntity?): String? {
        return if (contact == null) {
            null
        } else {
            val gson = Gson()
            gson.toJson(contact)
        }
    }

    @TypeConverter
    fun stringToContact(value: String?): ContactEntity? {
        return if (value == null) {
            return null
        } else {
            val gson = Gson()
            gson.fromJson(value, ContactEntity::class.java)
        }
    }
}