package com.example.chat.data.local.converter

import androidx.room.TypeConverter
import com.example.chat.data.local.model.MessageTypeEntity

class MessageTypeConverter {

    @TypeConverter
    fun messageTypeToString(type: MessageTypeEntity): Int {
        return type.ordinal
    }

    @TypeConverter
    fun stringToMessageType(value: String): MessageTypeEntity {
        return enumValueOf(value)
    }
}