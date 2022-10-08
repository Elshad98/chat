package com.example.chat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chat.data.local.dao.FriendDao
import com.example.chat.data.local.dao.MessageDao
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.message.Message

@Database(entities = [Friend::class, Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "chat_database"

        private var INSTANCE: AppDatabase? = null
        private var LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room
                    .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun friendDao(): FriendDao

    abstract fun messageDao(): MessageDao
}