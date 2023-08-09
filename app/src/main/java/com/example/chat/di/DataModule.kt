package com.example.chat.di

import com.example.chat.data.local.ChatDatabase
import com.example.chat.data.local.dao.FriendDao
import com.example.chat.data.local.dao.MessageDao
import com.example.chat.data.remote.common.NetworkHandler
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.service.FriendService
import com.example.chat.data.remote.service.MessageService
import com.example.chat.data.remote.service.UserService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import toothpick.config.Module

class DataModule : Module() {

    init {
        bind(ChatDatabase::class.java)
            .toProvider(ChatDatabaseProvider::class.java)
            .providesSingleton()

        bind(FriendDao::class.java)
            .toProvider(FriendDaoProvider::class.java)
            .providesSingleton()

        bind(MessageDao::class.java)
            .toProvider(MessageDaoProvider::class.java)
            .providesSingleton()

        bind(Gson::class.java)
            .toProvider(GsonProvider::class.java)
            .providesSingleton()

        bind(OkHttpClient::class.java)
            .toProvider(OkHttpClientProvider::class.java)
            .providesSingleton()

        bind(Retrofit::class.java)
            .toProvider(RetrofitProvider::class.java)
            .providesSingleton()

        bind(UserService::class.java)
            .toProvider(UserServiceProvider::class.java)
            .providesSingleton()

        bind(MessageService::class.java)
            .toProvider(MessageServiceProvider::class.java)
            .providesSingleton()

        bind(FriendService::class.java)
            .toProvider(FriendServiceProvider::class.java)
            .providesSingleton()

        bind(Request::class.java).singleton()

        bind(NetworkHandler::class.java).singleton()
    }
}