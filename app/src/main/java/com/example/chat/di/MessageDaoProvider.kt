package com.example.chat.di

import com.example.chat.data.local.ChatDatabase
import com.example.chat.data.local.dao.MessageDao
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class MessageDaoProvider(
    private val chatDatabase: ChatDatabase
) : Provider<MessageDao> {

    override fun get(): MessageDao {
        return chatDatabase.messageDao()
    }
}