package com.example.chat.presentation.firebase

import android.os.Handler
import android.os.Looper
import com.example.chat.domain.user.UpdateToken
import com.example.chat.presentation.App
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
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

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Handler(Looper.getMainLooper()).post {
            notificationHelper.sendNotification(remoteMessage)
        }
    }

    override fun onNewToken(token: String) {
        updateToken(UpdateToken.Params(token))
    }
}