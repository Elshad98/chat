package com.example.chat.di

import com.example.chat.data.account.AccountRepositoryImpl
import com.example.chat.data.friends.FriendsRepositoryImpl
import com.example.chat.data.media.MediaRepositoryImpl
import com.example.chat.data.messages.MessagesRepositoryImpl
import com.example.chat.domain.account.AccountRepository
import com.example.chat.domain.friends.FriendsRepository
import com.example.chat.domain.media.MediaRepository
import com.example.chat.domain.messages.MessagesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindMessagesRepository(impl: MessagesRepositoryImpl): MessagesRepository

    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindFriendsRepository(impl: FriendsRepositoryImpl): FriendsRepository

    @Binds
    @Singleton
    fun bindMediaRepository(impl: MediaRepositoryImpl): MediaRepository
}