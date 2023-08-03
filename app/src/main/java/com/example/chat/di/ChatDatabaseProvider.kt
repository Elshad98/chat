package com.example.chat.di

import android.content.Context
import androidx.room.Room
import com.example.chat.data.local.ChatDatabase
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class ChatDatabaseProvider(
    private val context: Context
) : Provider<ChatDatabase> {

    companion object {

        private const val DATABASE_NAME = "chat_database"
    }

    override fun get(): ChatDatabase {
        return Room
            .databaseBuilder(context, ChatDatabase::class.java, DATABASE_NAME)
            .build()
    }
}