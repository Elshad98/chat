package com.example.chat.di

import android.content.Context
import android.content.SharedPreferences
import com.example.chat.BuildConfig
import com.example.chat.data.cache.AccountCacheImpl
import com.example.chat.data.cache.AppDatabase
import com.example.chat.data.cache.SharedPrefsManager
import com.example.chat.data.remote.account.AccountRemoteImpl
import com.example.chat.data.remote.core.Request
import com.example.chat.data.remote.friends.FriendsRemoteImpl
import com.example.chat.data.remote.messages.MessagesRemoteImpl
import com.example.chat.data.remote.service.AccountService
import com.example.chat.data.remote.service.FriendsService
import com.example.chat.data.remote.service.MessageService
import com.example.chat.data.repository.account.AccountCache
import com.example.chat.data.repository.account.AccountRemote
import com.example.chat.data.repository.friends.FriendsCache
import com.example.chat.data.repository.friends.FriendsRemote
import com.example.chat.data.repository.messages.MessagesCache
import com.example.chat.data.repository.messages.MessagesRemote
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

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

    @Provides
    @Singleton
    fun provideAccountService(retrofit: Retrofit): AccountService {
        return retrofit.create(AccountService::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageService(retrofit: Retrofit): MessageService {
        return retrofit.create(MessageService::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendsService(retrofit: Retrofit): FriendsService {
        return retrofit.create(FriendsService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountRemote(request: Request, accountService: AccountService): AccountRemote {
        return AccountRemoteImpl(request, accountService)
    }

    @Provides
    @Singleton
    fun provideFriendsRemote(request: Request, friendsService: FriendsService): FriendsRemote {
        return FriendsRemoteImpl(request, friendsService)
    }

    @Provides
    @Singleton
    fun provideMessagesRemote(request: Request, messageService: MessageService): MessagesRemote {
        return MessagesRemoteImpl(request, messageService)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}