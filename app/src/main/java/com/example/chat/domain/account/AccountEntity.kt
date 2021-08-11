package com.example.chat.domain.account

import com.google.gson.annotations.SerializedName

class AccountEntity(
    @SerializedName("user_id")
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val status: String,
    @SerializedName("user_data")
    val userData: Long,
    val image: String
)