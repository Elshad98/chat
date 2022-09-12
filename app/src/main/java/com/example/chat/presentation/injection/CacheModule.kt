package com.example.chat.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import com.example.chat.cache.AccountCacheImpl
import com.example.chat.cache.ChatDatabase
import com.example.chat.cache.SharedPrefsManager
import com.example.chat.data.account.AccountCache
import com.example.chat.data.friends.FriendsCache
import com.example.chat.data.messages.MessagesCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule(
    private val prefName: String
) {

    @Provides
    @Singleton
    fun provideChatDatabase(context: Context): ChatDatabase {
        return ChatDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideMessagesCache(chatDatabase: ChatDatabase): MessagesCache {
        return chatDatabase.messagesDao
    }

    @Provides
    @Singleton
    fun provideFriendsCache(chatDatabase: ChatDatabase): FriendsCache {
        return chatDatabase.friendsDao
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAccountCache(
        prefsManager: SharedPrefsManager,
        chatDatabase: ChatDatabase
    ): AccountCache {
        return AccountCacheImpl(chatDatabase, prefsManager)
    }
}