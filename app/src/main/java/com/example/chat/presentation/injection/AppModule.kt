package com.example.chat.presentation.injection

import android.content.Context
import com.example.chat.data.account.AccountCache
import com.example.chat.data.account.AccountRemote
import com.example.chat.data.account.AccountRepositoryImpl
import com.example.chat.data.friends.FriendsRemote
import com.example.chat.data.friends.FriendsRepositoryImpl
import com.example.chat.data.media.MediaRepositoryImpl
import com.example.chat.domain.account.AccountRepository
import com.example.chat.domain.friends.FriendsRepository
import com.example.chat.domain.media.MediaRepository
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
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(
        remote: FriendsRemote,
        accountCache: AccountCache
    ): FriendsRepository {
        return FriendsRepositoryImpl(accountCache, remote)
    }
}