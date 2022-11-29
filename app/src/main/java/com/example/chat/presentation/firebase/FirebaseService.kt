package com.example.chat.presentation.firebase

import com.example.chat.domain.user.UpdateToken
import com.example.chat.presentation.App
import com.google.firebase.messaging.FirebaseMessagingService
import javax.inject.Inject

class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var updateToken: UpdateToken

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        App.appComponent.inject(this)
    }

    override fun onNewToken(token: String) {
        updateToken(UpdateToken.Params(token))
    }
}