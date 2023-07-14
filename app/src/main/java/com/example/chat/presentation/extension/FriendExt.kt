package com.example.chat.presentation.extension

import android.content.Context
import android.text.format.DateUtils
import com.example.chat.R
import com.example.chat.domain.friend.Friend

private const val ONE_MINUTE_IN_MILLIS = 60_000

fun Friend.getLastSeenText(context: Context): String {
    return if (System.currentTimeMillis() - ONE_MINUTE_IN_MILLIS < lastSeen) {
        context.getString(R.string.friend_status_last_seen_just_now)
    } else {
        context.getString(
            R.string.friend_status_last_seen,
            DateUtils.getRelativeTimeSpanString(lastSeen)
        )
    }
}
