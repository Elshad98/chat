package com.example.chat.domain.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    var id: Long,
    var name: String,
    var email: String,
    var token: String,
    var status: String,
    @SerializedName("user_data")
    var userData: Long,
    var image: String,
    var password: String
)