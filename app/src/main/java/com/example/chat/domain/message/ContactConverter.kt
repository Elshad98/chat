package com.example.chat.domain.message

import androidx.room.TypeConverter
import com.google.gson.Gson

class ContactConverter {

    @TypeConverter
    fun contactToString(contact: Contact?): String? {
        return if (contact == null) {
            null
        } else {
            val gson = Gson()
            gson.toJson(contact)
        }
    }

    @TypeConverter
    fun stringToContact(value: String?): Contact? {
        return if (value == null) {
            return null
        } else {
            val gson = Gson()
            gson.fromJson(value, Contact::class.java)
        }
    }
}