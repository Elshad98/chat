package com.example.chat.di

import com.example.chat.data.remote.service.UserService
import retrofit2.Retrofit
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class UserServiceProvider(
    private val retrofit: Retrofit
) : Provider<UserService> {

    override fun get(): UserService {
        return retrofit.create(UserService::class.java)
    }
}