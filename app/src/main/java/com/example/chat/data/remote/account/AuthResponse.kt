package com.example.chat.data.remote.account

import com.example.chat.domain.account.AccountEntity
import com.example.chat.data.remote.core.BaseResponse

class AuthResponse(
    success: Int,
    message: String,
    val user: AccountEntity
) : BaseResponse(success, message)