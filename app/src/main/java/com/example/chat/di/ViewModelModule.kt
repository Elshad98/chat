package com.example.chat.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat.di.annotations.ViewModelKey
import com.example.chat.presentation.forgetpassword.ForgetPasswordViewModel
import com.example.chat.presentation.friend.FriendListViewModel
import com.example.chat.presentation.friend.FriendViewModel
import com.example.chat.presentation.friendrequest.FriendRequestListViewModel
import com.example.chat.presentation.home.HomeViewModel
import com.example.chat.presentation.invitefriend.InviteFriendViewModel
import com.example.chat.presentation.login.LoginViewModel
import com.example.chat.presentation.messages.MessageListViewModel
import com.example.chat.presentation.register.RegisterViewModel
import com.example.chat.presentation.settings.SettingsViewModel
import com.example.chat.presentation.settings.email.ChangeEmailViewModel
import com.example.chat.presentation.settings.password.ChangePasswordViewModel
import com.example.chat.presentation.settings.status.UpdateStatusViewModel
import com.example.chat.presentation.settings.username.ChangeUsernameViewModel
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
    @ViewModelKey(FriendViewModel::class)
    fun bindFriendViewModel(friendViewModel: FriendViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendListViewModel::class)
    fun bindFriendListViewModel(friendListViewModel: FriendListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageListViewModel::class)
    fun bindMessageListViewModel(messageListViewModel: MessageListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeEmailViewModel::class)
    fun bindChangeEmailViewModel(changeEmailViewModel: ChangeEmailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateStatusViewModel::class)
    fun bindUpdateStatusViewModel(updateStatusViewModel: UpdateStatusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeUsernameViewModel::class)
    fun bindChangeUsernameViewModel(changeUsernameViewModel: ChangeUsernameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    fun bindChangePasswordViewModel(changePasswordViewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InviteFriendViewModel::class)
    fun bindInviteFriendViewModel(inviteFriendViewModel: InviteFriendViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendRequestListViewModel::class)
    fun bindFriendRequestListViewModel(friendRequestListViewModel: FriendRequestListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel::class)
    fun bindForgetPasswordViewModel(forgetPasswordViewModel: ForgetPasswordViewModel): ViewModel
}