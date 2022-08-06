package com.example.chat.remote.messages

import com.example.chat.domain.messages.MessageEntity
import com.example.chat.remote.core.BaseResponse

class GetMessagesResponse(
    success: Int,
    message: String,
    val messages: List<MessageEntity>
) : BaseResponse(success, message)