package com.example.chat.presentation.injection

import android.content.Context
import com.example.chat.data.account.AccountCache
import com.example.chat.data.account.AccountRemote
import com.example.chat.data.account.AccountRepositoryImpl
import com.example.chat.domain.account.AccountRepository
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
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository =
        AccountRepositoryImpl(remote, cache)
}