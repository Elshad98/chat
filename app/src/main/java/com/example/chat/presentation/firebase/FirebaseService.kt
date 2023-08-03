package com.example.chat.presentation.firebase

import com.example.chat.domain.user.UpdateToken
import com.google.firebase.messaging.FirebaseMessagingService
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class FirebaseService : FirebaseMessagingService() {

    private val updateToken by inject<UpdateToken>()

    override fun onCreate() {
        super.onCreate()
        KTP.openRootScope().inject(this)
    }

    override fun onNewToken(token: String) {
        updateToken(UpdateToken.Params(token))
    }
}