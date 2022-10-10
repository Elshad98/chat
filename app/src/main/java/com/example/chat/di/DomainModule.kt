package com.example.chat.di

import com.example.chat.data.repository.FriendRepositoryImpl
import com.example.chat.data.repository.media.MediaRepositoryImpl
import com.example.chat.data.repository.MessageRepositoryImpl
import com.example.chat.data.repository.UserRepositoryImpl
import com.example.chat.domain.friend.FriendRepository
import com.example.chat.domain.media.MediaRepository
import com.example.chat.domain.message.MessageRepository
import com.example.chat.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindMessageRepository(impl: MessageRepositoryImpl): MessageRepository

    @Binds
    @Singleton
    fun bindFriendRepository(impl: FriendRepositoryImpl): FriendRepository

    @Binds
    @Singleton
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindMediaRepository(impl: MediaRepositoryImpl): MediaRepository
}