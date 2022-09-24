package com.example.chat.presentation

import android.app.Application
import com.example.chat.di.AppComponent
import com.example.chat.di.AppModule
import com.example.chat.di.CacheModule
import com.example.chat.di.DaggerAppComponent

class App : Application() {

    companion object {

        private const val APP_PREFERENCES = "AppPreferences"

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .cacheModule(CacheModule(APP_PREFERENCES))
            .build()
    }
}