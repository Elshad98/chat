package com.example.chat.presentation.message

import com.example.chat.domain.message.Message

interface OnMessageClickListener {

    fun onMessageClick(message: Message)

    fun onMessageLongClick(message: Message)
}