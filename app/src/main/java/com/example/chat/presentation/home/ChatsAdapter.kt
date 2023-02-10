package com.example.chat.presentation.home

import android.view.ViewGroup
import com.example.chat.core.extension.inflater
import com.example.chat.core.platform.BaseAdapter
import com.example.chat.databinding.ItemChatBinding
import com.example.chat.domain.message.Message

class ChatsAdapter : BaseAdapter<Message, ChatViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ItemChatBinding
            .inflate(parent.inflater, parent, false)
            .let(::ChatViewHolder)
    }
}