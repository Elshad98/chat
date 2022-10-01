package com.example.chat.domain.message

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("user_id")
    var id: Long,
    var name: String,
    var image: String,
    @SerializedName("last_seen")
    var lastSeen: Long
)