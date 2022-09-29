package com.example.chat.data.remote.messages

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.messages.MessageEntity

class GetMessagesResponse(
    success: Int,
    message: String,
    val messages: List<MessageEntity>
) : BaseResponse(success, message)