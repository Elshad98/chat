package com.example.chat.presentation.home

import com.example.chat.R
import com.example.chat.core.extension.load
import com.example.chat.core.platform.BaseViewHolder
import com.example.chat.databinding.ItemChatBinding
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageType

class ChatViewHolder(
    private val binding: ItemChatBinding
) : BaseViewHolder<Message>(binding.root) {

    override fun bind(item: Message) {
        with(binding) {
            textMessage.text = when (item.type) {
                MessageType.TEXT -> {
                    if (item.fromMe) {
                        itemView.context.getString(R.string.chat_list_you, item.message)
                    } else {
                        item.message
                    }
                }
                MessageType.IMAGE -> {
                    val photo = itemView.context.getString(R.string.chat_list_photo)
                    if (item.fromMe) {
                        itemView.context.getString(R.string.chat_list_you, photo)
                    } else {
                        photo
                    }
                }
            }
            textName.text = item.contact.name
            imageUser.load(item.contact.image, R.drawable.user_placeholder)
        }
    }
}