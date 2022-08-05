package com.example.chat.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chat.cache.friends.FriendsDao
import com.example.chat.cache.messages.MessagesDao
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.messages.MessageEntity

@Database(entities = [FriendEntity::class, MessageEntity::class], version = 1, exportSchema = false)
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

    abstract val friendsDao: FriendsDao
    abstract val messagesDao: MessagesDao
}