package com.example.chat.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chat.data.cache.friends.FriendsDao
import com.example.chat.data.cache.messages.MessagesDao
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.messages.MessageEntity

@Database(entities = [FriendEntity::class, MessageEntity::class], version = 1, exportSchema = false)
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

    abstract fun friendsDao(): FriendsDao

    abstract fun messagesDao(): MessagesDao
}