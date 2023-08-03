package com.example.chat.di

import com.example.chat.data.remote.service.FriendService
import retrofit2.Retrofit
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class FriendServiceProvider(
    private val retrofit: Retrofit
) : Provider<FriendService> {

    override fun get(): FriendService {
        return retrofit.create(FriendService::class.java)
    }
}