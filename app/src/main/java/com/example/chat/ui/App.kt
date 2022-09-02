package com.example.chat.ui

import android.app.Application
import com.example.chat.presentation.injection.AppModule
import com.example.chat.presentation.injection.CacheModule
import com.example.chat.presentation.injection.DomainModule
import com.example.chat.presentation.injection.RemoteModule
import com.example.chat.presentation.injection.ViewModelModule
import com.example.chat.ui.account.AccountActivity
import com.example.chat.ui.account.AccountFragment
import com.example.chat.ui.core.navigation.RouterActivity
import com.example.chat.ui.firebase.FirebaseService
import com.example.chat.ui.friends.FriendRequestsFragment
import com.example.chat.ui.friends.FriendsFragment
import com.example.chat.ui.home.ChatsFragment
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.ForgetPasswordFragment
import com.example.chat.ui.login.LoginFragment
import com.example.chat.ui.messages.MessagesActivity
import com.example.chat.ui.messages.MessagesFragment
import com.example.chat.ui.register.RegisterActivity
import com.example.chat.ui.register.RegisterFragment
import com.example.chat.ui.user.UserActivity
import com.example.chat.ui.user.UserFragment
import dagger.Component
import javax.inject.Singleton

class App : Application() {

    companion object {

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .build()
    }
}

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