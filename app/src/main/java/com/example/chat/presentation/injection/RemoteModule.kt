package com.example.chat.presentation.injection

import com.example.chat.BuildConfig
import com.example.chat.data.account.AccountRemote
import com.example.chat.data.friends.FriendsRemote
import com.example.chat.remote.account.AccountRemoteImpl
import com.example.chat.remote.core.Request
import com.example.chat.remote.friends.FriendsRemoteImpl
import com.example.chat.remote.service.ApiService
import com.example.chat.remote.service.ServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideAppService(): ApiService = ServiceFactory.makeService(BuildConfig.DEBUG)

    @Provides
    @Singleton
    fun provideAccountRemote(request: Request, apiService: ApiService): AccountRemote {
        return AccountRemoteImpl(request, apiService)
    }

    @Singleton
    @Provides
    fun provideFriendsRemote(request: Request, apiService: ApiService): FriendsRemote {
        return FriendsRemoteImpl(request, apiService)
    }
}