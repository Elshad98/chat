package com.example.chat.ui.messages

import androidx.recyclerview.widget.DiffUtil
import com.example.chat.domain.messages.MessageEntity

class MessageDiffCallback : DiffUtil.ItemCallback<MessageEntity>() {

    override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem == newItem
    }
}