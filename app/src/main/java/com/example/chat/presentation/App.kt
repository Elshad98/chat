package com.example.chat.presentation

import android.app.Application
import com.example.chat.di.AppComponent
import com.example.chat.di.AppModule
import com.example.chat.di.CacheModule
import com.example.chat.di.DaggerAppComponent
import com.example.chat.di.RemoteModule

class App : Application() {

    companion object {

        private const val APP_PREFERENCES = "AppPreferences"
        private const val BASE_URL = "http://n964182b.bget.ru/rest_api/"

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .remoteModule(RemoteModule(BASE_URL))
            .cacheModule(CacheModule(APP_PREFERENCES))
            .build()
    }
}