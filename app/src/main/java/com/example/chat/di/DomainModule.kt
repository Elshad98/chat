package com.example.chat.di

import com.example.chat.data.repository.FriendRepositoryImpl
import com.example.chat.data.repository.MessageRepositoryImpl
import com.example.chat.data.repository.UserRepositoryImpl
import com.example.chat.data.repository.media.MediaRepositoryImpl
import com.example.chat.domain.friend.FriendRepository
import com.example.chat.domain.media.MediaRepository
import com.example.chat.domain.message.MessageRepository
import com.example.chat.domain.user.UserRepository
import toothpick.config.Module

class DomainModule : Module() {

    init {
        bind(MessageRepository::class.java)
            .to(MessageRepositoryImpl::class.java)
            .singleton()

        bind(FriendRepository::class.java)
            .to(FriendRepositoryImpl::class.java)
            .singleton()

        bind(MediaRepository::class.java)
            .to(MediaRepositoryImpl::class.java)
            .singleton()

        bind(UserRepository::class.java)
            .to(UserRepositoryImpl::class.java)
            .singleton()
    }
}