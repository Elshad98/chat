package com.example.chat.data.remote.user

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.user.User

class AuthResponse(
    success: Int,
    message: String,
    val user: User
) : BaseResponse(success, message)