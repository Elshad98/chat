package com.example.chat.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat.di.annotations.ViewModelKey
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.presentation.viewmodel.FriendsViewModel
import com.example.chat.presentation.viewmodel.MediaViewModel
import com.example.chat.presentation.viewmodel.MessagesViewModel
import com.example.chat.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    fun bindMessagesViewModel(messagesViewModel: MessagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendsViewModel::class)
    fun bindFriendsViewModel(friendsViewModel: FriendsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MediaViewModel::class)
    fun bindMediaViewModel(mediaViewModel: MediaViewModel): ViewModel
}