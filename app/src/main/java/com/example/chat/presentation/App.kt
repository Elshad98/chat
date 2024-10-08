package com.example.chat.presentation

import android.app.Application
import android.content.Context
import com.example.chat.di.AppModule
import com.example.chat.di.DataModule
import com.example.chat.di.DomainModule
import toothpick.Scope
import toothpick.ktp.KTP

class App : Application() {

    private lateinit var scope: Scope

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        openAppScope()
    }

    private fun openAppScope() {
        scope = KTP.openScope(this).installModules(
            AppModule(this),
            DataModule(),
            DomainModule()
        )
    }
}