package com.example.chat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chat.data.local.converter.ContactConverter
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
    ContactConverter::class,
    MessageTypeConverter::class
)
abstract class ChatDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "chat_database"

        private var INSTANCE: ChatDatabase? = null
        private var LOCK = Any()

        fun getInstance(context: Context): ChatDatabase {
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room
                    .databaseBuilder(context, ChatDatabase::class.java, DB_NAME)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun friendDao(): FriendDao

    abstract fun messageDao(): MessageDao
}