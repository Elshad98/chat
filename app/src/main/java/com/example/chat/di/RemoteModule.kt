package com.example.chat.di

import com.example.chat.BuildConfig
import com.example.chat.data.account.AccountRemote
import com.example.chat.data.friends.FriendsRemote
import com.example.chat.data.messages.MessagesRemote
import com.example.chat.remote.account.AccountRemoteImpl
import com.example.chat.remote.core.Request
import com.example.chat.remote.friends.FriendsRemoteImpl
import com.example.chat.remote.messages.MessagesRemoteImpl
import com.example.chat.remote.service.AccountService
import com.example.chat.remote.service.FriendsService
import com.example.chat.remote.service.MessageService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RemoteModule {

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