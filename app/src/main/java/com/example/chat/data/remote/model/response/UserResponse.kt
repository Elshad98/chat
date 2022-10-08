package com.example.chat.data.remote.model.response

import com.example.chat.data.remote.model.dto.UserDto

class UserResponse(
    success: Int,
    message: String,
    val user: UserDto
) : BaseResponse(success, message)