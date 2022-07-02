package com.example.chat.ui.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.remote.service.ApiService
import com.example.chat.ui.home.HomeActivity
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject
import org.json.JSONObject

class NotificationHelper @Inject constructor(
    val context: Context
) {

    companion object {

        const val TYPE_ADD_FRIEND = "addFriend"

        private const val TYPE = "type"
        private const val MESSAGE = "message"
        private const val JSON_MESSAGE = "firebase_json_message"
        private const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        private const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"

        private const val NOTIFICATION_ID = 110
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createChannel()
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

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_ADD_FRIEND)

        createNotification(
            context.getString(R.string.friend_request),
            "${friend.name} ${context.getString(R.string.wants_add_as_friend)}",
            intent
        )
    }

    private fun sendApproveFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_ADD_FRIEND)

        createNotification(
            context.getString(R.string.friend_request_approved),
            "${friend.name} ${context.getString(R.string.approved_friend_request)}",
            intent
        )
    }

    private fun sendCancelledFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_CANCELLED_FRIEND_REQUEST)

        createNotification(
            context.getString(R.string.friend_request_cancelled),
            "${friend.name} ${context.getString(R.string.cancelled_friend_request)}",
            intent
        )
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

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                "${context.packageName}.notification_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // Sets whether notifications posted to this channel should display notification lights
                enableLights(true)
                // Sets whether notification posted to this channel should vibrate.
                enableVibration(true)
                // Sets the notification light color for notifications posted to this channel
                lightColor = Color.GREEN
                // Sets whether notifications posted to this channel appear on the lockscreen or not
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(title: String, message: String, intent: Intent) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        intent.action = "notification $NOTIFICATION_ID"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        val contentIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, context.packageName)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setContentText(message)
            .setContentIntent(contentIntent)
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}