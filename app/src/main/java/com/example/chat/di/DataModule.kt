package com.example.chat.di

import android.content.Context
import android.content.SharedPreferences
import com.example.chat.BuildConfig
import com.example.chat.data.local.AppDatabase
import com.example.chat.data.local.SharedPrefsManager
import com.example.chat.data.local.UserCacheImpl
import com.example.chat.data.remote.service.FriendService
import com.example.chat.data.remote.service.MessageService
import com.example.chat.data.remote.service.UserService
import com.example.chat.data.repository.friend.FriendCache
import com.example.chat.data.repository.message.MessageCache
import com.example.chat.data.repository.user.UserCache
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
    fun provideMessageCache(chatDatabase: AppDatabase): MessageCache {
        return chatDatabase.messageDao()
    }

    @Provides
    @Singleton
    fun provideFriendCache(appDatabase: AppDatabase): FriendCache {
        return appDatabase.friendDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserCache(
        prefsManager: SharedPrefsManager,
        appDatabase: AppDatabase
    ): UserCache {
        return UserCacheImpl(prefsManager, appDatabase)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageService(retrofit: Retrofit): MessageService {
        return retrofit.create(MessageService::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendService(retrofit: Retrofit): FriendService {
        return retrofit.create(FriendService::class.java)
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