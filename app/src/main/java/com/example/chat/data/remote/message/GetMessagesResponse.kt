package com.example.chat.data.remote.message

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.message.Message

class GetMessagesResponse(
    success: Int,
    message: String,
    val messages: List<Message>
) : BaseResponse(success, message)