package com.example.chat.domain.account

import com.google.gson.annotations.SerializedName

data class AccountEntity(
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