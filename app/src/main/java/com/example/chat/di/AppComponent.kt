package com.example.chat.di

import com.example.chat.presentation.firebase.FirebaseService
import com.example.chat.presentation.forgetpassword.ForgetPasswordFragment
import com.example.chat.presentation.home.HomeFragment
import com.example.chat.presentation.invitefriend.InviteFriendFragment
import com.example.chat.presentation.login.LoginFragment
import com.example.chat.presentation.register.RegisterFragment
import com.example.chat.presentation.splash.SplashFragment
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

    fun inject(fragment: SplashFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: RegisterFragment)

    fun inject(fragment: InviteFriendFragment)

    fun inject(fragment: ForgetPasswordFragment)
}