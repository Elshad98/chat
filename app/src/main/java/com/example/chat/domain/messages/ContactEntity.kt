package com.example.chat.domain.messages

import com.google.gson.annotations.SerializedName

data class ContactEntity(
    @SerializedName("user_id")
    var id: Long,
    var name: String,
    var image: String,
    @SerializedName("last_seen")
    var lastSeen: Long
)