package com.example.chat.presentation.home

import android.view.ViewGroup
import com.example.chat.core.extension.inflater
import com.example.chat.core.platform.BaseAdapter
import com.example.chat.databinding.ChatListItemBinding
import com.example.chat.domain.message.Message

class ChatListItemAdapter : BaseAdapter<Message, ChatListItemViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListItemViewHolder {
        val binding = ChatListItemBinding.inflate(parent.inflater, parent, false)
        return ChatListItemViewHolder(binding)
    }
}