package com.example.chat.presentation.firebase

import android.content.Context
import com.example.chat.core.extension.getNotificationManager
import toothpick.InjectConstructor

@InjectConstructor
class NotificationHelper(context: Context) {

    companion object {

        const val TYPE_ADD_FRIEND = "addFriend"
        const val TYPE_SEND_MESSAGE = "sendMessage"

        private const val MESSAGE = "message"

        private const val JSON_MESSAGE = "firebase_json_message"
        private const val TYPE = "type"
        private const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        private const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"

        private const val NOTIFICATION_ID = 110
    }

    private val notificationManager = context.getNotificationManager()
}