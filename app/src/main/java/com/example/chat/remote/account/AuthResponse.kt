package com.example.chat.remote.account

import com.example.chat.domain.account.AccountEntity
import com.example.chat.remote.core.BaseResponse

class AuthResponse(
    success: Int,
    message: String,
    val user: AccountEntity
) : BaseResponse(success, message)