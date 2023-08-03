package com.example.chat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chat.data.local.converter.ContactConverter
import com.example.chat.data.local.converter.DateConverter
import com.example.chat.data.local.converter.MessageTypeConverter
import com.example.chat.data.local.dao.FriendDao
import com.example.chat.data.local.dao.MessageDao
import com.example.chat.data.local.model.FriendEntity
import com.example.chat.data.local.model.MessageEntity

@Database(
    entities = [
        FriendEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    ContactConverter::class,
    MessageTypeConverter::class
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun friendDao(): FriendDao

    abstract fun messageDao(): MessageDao
}