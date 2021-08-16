package com.example.chat.ui.firebase

import android.util.Log
import com.example.chat.ui.App
import com.example.chat.domain.account.UpdateToken
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class FirebaseService : FirebaseMessagingService() {

    companion object {

        private const val TAG = "FirebaseService"
    }

    @Inject
    lateinit var updateToken: UpdateToken

    override fun onCreate() {
        super.onCreate()
        App.appComponent.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, "Token: $token")
        updateToken(UpdateToken.Params(token))
    }
}