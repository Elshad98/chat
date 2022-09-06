package com.example.chat.ui.firebase

import android.os.Handler
import android.os.Looper
import com.example.chat.domain.account.UpdateToken
import com.example.chat.ui.App
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