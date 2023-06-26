package com.example.chat.presentation.message

import androidx.recyclerview.widget.DiffUtil
import com.example.chat.domain.message.Message

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}