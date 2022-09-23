package com.example.chat.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context
}