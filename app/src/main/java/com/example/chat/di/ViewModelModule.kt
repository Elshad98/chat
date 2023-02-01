package com.example.chat.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat.di.annotations.ViewModelKey
import com.example.chat.presentation.forgetpassword.ForgetPasswordViewModel
import com.example.chat.presentation.friend.FriendListViewModel
import com.example.chat.presentation.home.HomeViewModel
import com.example.chat.presentation.invitefriend.InviteFriendViewModel
import com.example.chat.presentation.login.LoginViewModel
import com.example.chat.presentation.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendListViewModel::class)
    fun bindFriendListViewModel(friendListViewModel: FriendListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InviteFriendViewModel::class)
    fun bindInviteFriendViewModel(inviteFriendViewModel: InviteFriendViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel::class)
    fun bindForgetPasswordViewModel(forgetPasswordViewModel: ForgetPasswordViewModel): ViewModel
}