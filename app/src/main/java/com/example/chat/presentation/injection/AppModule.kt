package com.example.chat.presentation.injection

import android.content.Context
import com.example.chat.data.account.AccountCache
import com.example.chat.data.account.AccountRemote
import com.example.chat.data.account.AccountRepositoryImpl
import com.example.chat.data.friends.FriendsCache
import com.example.chat.data.friends.FriendsRemote
import com.example.chat.data.friends.FriendsRepositoryImpl
import com.example.chat.data.media.MediaRepositoryImpl
import com.example.chat.data.messages.MessagesCache
import com.example.chat.data.messages.MessagesRemote
import com.example.chat.data.messages.MessagesRepositoryImpl
import com.example.chat.domain.account.AccountRepository
import com.example.chat.domain.friends.FriendsRepository
import com.example.chat.domain.media.MediaRepository
import com.example.chat.domain.messages.MessagesRepository
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

    @Provides
    @Singleton
    fun provideMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountRemote: AccountRemote,
        accountCache: AccountCache
    ): AccountRepository {
        return AccountRepositoryImpl(accountCache, accountRemote)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(
        friendsRemote: FriendsRemote,
        accountCache: AccountCache,
        friendsCache: FriendsCache
    ): FriendsRepository {
        return FriendsRepositoryImpl(friendsCache, accountCache, friendsRemote)
    }

    @Provides
    @Singleton
    fun provideMessagesRepository(
        messagesRemote: MessagesRemote,
        messagesCache: MessagesCache,
        accountCache: AccountCache
    ): MessagesRepository {
        return MessagesRepositoryImpl(messagesRemote, messagesCache, accountCache)
    }
}