package com.example.chat.data.remote.model.response

import com.example.chat.data.remote.model.dto.MessageDto

class MessagesResponse(
    success: Int,
    message: String,
    val messages: List<MessageDto>
) : BaseResponse(success, message)