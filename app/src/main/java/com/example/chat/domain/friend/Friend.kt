package com.example.chat.domain.friend

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Friend(
    val id: Long,
    val name: String,
    val email: String,
    val friendsId: Long,
    val status: String,
    val image: String,
    val isRequest: Int,
    val lastSeen: Date
) : Parcelable