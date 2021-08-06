package com.example.chat

import android.app.Application
import com.example.chat.presentation.injection.AppModule
import com.example.chat.presentation.injection.CacheModule
import com.example.chat.presentation.injection.RemoteModule
import com.example.chat.presentation.injection.ViewModelModule
import com.example.chat.ui.activity.RegisterActivity
import com.example.chat.ui.fragment.RegisterFragment
import com.example.chat.ui.service.FirebaseService
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

    // Fragment
    fun inject(fragment: RegisterFragment)

    // Services
    fun inject(service: FirebaseService)
}