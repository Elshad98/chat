package com.example.chat.di

import com.example.chat.data.remote.serializer.DateDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import toothpick.InjectConstructor
import java.util.Date
import javax.inject.Provider

@InjectConstructor
class GsonProvider : Provider<Gson> {

    override fun get(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .create()
    }
}