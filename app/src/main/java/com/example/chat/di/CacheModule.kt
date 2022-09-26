package com.example.chat.di

import android.content.Context
import android.content.SharedPreferences
import com.example.chat.cache.AccountCacheImpl
import com.example.chat.cache.AppDatabase
import com.example.chat.cache.SharedPrefsManager
import com.example.chat.data.account.AccountCache
import com.example.chat.data.friends.FriendsCache
import com.example.chat.data.messages.MessagesCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideMessagesCache(chatDatabase: AppDatabase): MessagesCache {
        return chatDatabase.messagesDao()
    }

    @Provides
    @Singleton
    fun provideFriendsCache(appDatabase: AppDatabase): FriendsCache {
        return appDatabase.friendsDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAccountCache(
        prefsManager: SharedPrefsManager,
        appDatabase: AppDatabase
    ): AccountCache {
        return AccountCacheImpl(appDatabase, prefsManager)
    }
}