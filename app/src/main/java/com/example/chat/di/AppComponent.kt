package com.example.chat.di

import com.example.chat.presentation.account.AccountActivity
import com.example.chat.presentation.account.AccountFragment
import com.example.chat.presentation.core.navigation.RouterActivity
import com.example.chat.presentation.firebase.FirebaseService
import com.example.chat.presentation.friends.FriendRequestsFragment
import com.example.chat.presentation.friends.FriendsFragment
import com.example.chat.presentation.home.ChatsFragment
import com.example.chat.presentation.home.HomeActivity
import com.example.chat.presentation.login.ForgetPasswordFragment
import com.example.chat.presentation.login.LoginFragment
import com.example.chat.presentation.messages.MessagesActivity
import com.example.chat.presentation.messages.MessagesFragment
import com.example.chat.presentation.register.RegisterActivity
import com.example.chat.presentation.register.RegisterFragment
import com.example.chat.presentation.user.UserActivity
import com.example.chat.presentation.user.UserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: RegisterActivity)

    fun inject(activity: MessagesActivity)

    fun inject(activity: AccountActivity)

    fun inject(activity: RouterActivity)

    fun inject(activity: UserActivity)

    fun inject(activity: HomeActivity)

    fun inject(fragment: UserFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: ChatsFragment)

    fun inject(fragment: FriendsFragment)

    fun inject(fragment: AccountFragment)

    fun inject(fragment: RegisterFragment)

    fun inject(fragment: MessagesFragment)

    fun inject(fragment: FriendRequestsFragment)

    fun inject(fragment: ForgetPasswordFragment)

    fun inject(service: FirebaseService)
}