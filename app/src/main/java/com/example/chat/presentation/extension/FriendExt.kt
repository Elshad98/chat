package com.example.chat.presentation.extension

import android.content.Context
import android.text.format.DateUtils
import com.example.chat.R
import com.example.chat.core.extension.isInLastMinute
import com.example.chat.domain.friend.Friend

fun Friend.getLastSeenText(context: Context): String {
    return if (lastSeen.isInLastMinute()) {
        context.getString(R.string.friend_status_last_seen_just_now)
    } else {
        context.getString(
            R.string.friend_status_last_seen,
            DateUtils.getRelativeTimeSpanString(lastSeen.time)
        )
    }
}