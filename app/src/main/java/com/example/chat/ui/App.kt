package com.example.chat.ui

import android.app.Application
import com.example.chat.presentation.injection.AppModule
import com.example.chat.presentation.injection.CacheModule
import com.example.chat.presentation.injection.RemoteModule
import com.example.chat.presentation.injection.ViewModelModule
import com.example.chat.ui.core.navigation.RouterActivity
import com.example.chat.ui.firebase.FirebaseService
import com.example.chat.ui.friends.FriendRequestsFragment
import com.example.chat.ui.friends.FriendsFragment
import com.example.chat.ui.home.ChatsFragment
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.LoginFragment
import com.example.chat.ui.register.RegisterActivity
import com.example.chat.ui.register.RegisterFragment
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
            .appModule(AppModule(this))
            .build()
    }
}

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    // Activities
    fun inject(activity: RegisterActivity)

    fun inject(activity: RouterActivity)

    fun inject(activity: HomeActivity)

    // Fragment
    fun inject(fragment: RegisterFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: ChatsFragment)

    fun inject(fragment: FriendsFragment)

    fun inject(fragment: FriendRequestsFragment)

    // Services
    fun inject(service: FirebaseService)
}