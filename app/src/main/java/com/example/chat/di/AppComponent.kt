package com.example.chat.di

import com.example.chat.presentation.firebase.FirebaseService
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
}