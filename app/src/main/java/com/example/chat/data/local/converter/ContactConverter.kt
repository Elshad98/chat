package com.example.chat.data.local.converter

import androidx.room.TypeConverter
import com.example.chat.data.local.model.ContactEntity
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
            null
        } else {
            val gson = Gson()
            gson.fromJson(value, ContactEntity::class.java)
        }
    }
}