package com.example.chat.di

import com.example.chat.data.remote.service.MessageService
import retrofit2.Retrofit
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class MessageServiceProvider(
    private val retrofit: Retrofit
) : Provider<MessageService> {

    override fun get(): MessageService {
        return retrofit.create(MessageService::class.java)
    }
}