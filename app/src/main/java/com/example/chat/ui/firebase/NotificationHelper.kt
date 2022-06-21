package com.example.chat.ui.firebase

import android.content.Context
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.extensions.longToast
import com.example.chat.remote.service.ApiService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject
import org.json.JSONObject

class NotificationHelper @Inject constructor(
    val context: Context
) {

    companion object {

        private const val TYPE = "type"
        private const val MESSAGE = "message"
        private const val TYPE_ADD_FRIEND = "addFriend"
        private const val JSON_MESSAGE = "firebase_json_message"
        private const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        private const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"
    }

    fun sendNotification(remoteMessage: RemoteMessage?) {
        if (remoteMessage?.data == null) {
            return
        }

        val message = remoteMessage.data[MESSAGE]
        val jsonMessage = JSONObject(message).getJSONObject(JSON_MESSAGE)

        when (jsonMessage.getString(TYPE)) {
            TYPE_ADD_FRIEND -> sendAddFriendNotification(jsonMessage)
            TYPE_APPROVED_FRIEND -> sendApproveFriendNotification(jsonMessage)
            TYPE_CANCELLED_FRIEND_REQUEST -> sendCancelledFriendNotification(jsonMessage)
        }
    }

    private fun sendAddFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)
        context.longToast("${friend.name} ${context.getString(R.string.wants_add_as_friend)}")
    }

    private fun sendApproveFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)
        context.longToast("${friend.name} ${context.getString(R.string.approved_friend_request)}")
    }

    private fun sendCancelledFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)
        context.longToast("${friend.name} ${context.getString(R.string.cancelled_friend_request)}")
    }

    private fun parseFriend(jsonMessage: JSONObject): FriendEntity {
        val requestUser = if (jsonMessage.has(ApiService.PARAM_REQUEST_USER)) {
            jsonMessage.getJSONObject(ApiService.PARAM_REQUEST_USER)
        } else {
            jsonMessage.getJSONObject(ApiService.PARAM_APPROVED_USER)
        }

        return FriendEntity(
            id = requestUser.getLong(ApiService.PARAM_USER_ID),
            name = requestUser.getString(ApiService.PARAM_NAME),
            email = requestUser.getString(ApiService.PARAM_EMAIL),
            image = requestUser.getString(ApiService.PARAM_USER_ID),
            status = requestUser.getString(ApiService.PARAM_STATUS),
            friendsId = jsonMessage.getLong(ApiService.PARAM_FRIENDS_ID)
        )
    }
}