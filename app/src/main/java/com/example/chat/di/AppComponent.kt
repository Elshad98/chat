package com.example.chat.di

import com.example.chat.presentation.firebase.FirebaseService
import com.example.chat.presentation.forgetpassword.ForgetPasswordFragment
import com.example.chat.presentation.friendrequests.FriendRequestsFragment
import com.example.chat.presentation.friends.FriendsFragment
import com.example.chat.presentation.home.HomeFragment
import com.example.chat.presentation.invitefriend.InviteFriendFragment
import com.example.chat.presentation.login.LoginFragment
import com.example.chat.presentation.register.RegisterFragment
import com.example.chat.presentation.settings.email.ChangeEmailFragment
import com.example.chat.presentation.settings.password.ChangePasswordFragment
import com.example.chat.presentation.settings.status.UpdateStatusFragment
import com.example.chat.presentation.settings.username.ChangeUsernameFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(service: FirebaseService)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: ChangeEmailFragment)

    fun inject(fragment: UpdateStatusFragment)

    fun inject(fragment: ChangeUsernameFragment)

    fun inject(fragment: ChangePasswordFragment)

    fun inject(fragment: RegisterFragment)

    fun inject(fragment: FriendsFragment)

    fun inject(fragment: InviteFriendFragment)

    fun inject(fragment: FriendRequestsFragment)

    fun inject(fragment: ForgetPasswordFragment)
}