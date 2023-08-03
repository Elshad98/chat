package com.example.chat.di

import com.example.chat.data.local.ChatDatabase
import com.example.chat.data.local.dao.FriendDao
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class FriendDaoProvider(
    private val chatDatabase: ChatDatabase
) : Provider<FriendDao> {

    override fun get(): FriendDao {
        return chatDatabase.friendDao()
    }
}