package com.example.chat.presentation.home

import android.view.ViewGroup
import com.example.chat.core.extension.inflater
import com.example.chat.databinding.ItemChatBinding
import com.example.chat.domain.message.Message
import com.example.chat.presentation.messages.MessagesAdapter

class ChatsAdapter : MessagesAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemChatBinding.inflate(parent.inflater, parent, false)
        return ChatViewHolder(binding)
    }

    class ChatViewHolder(
        private val binding: ItemChatBinding
    ) : BaseViewHolder(binding.root) {

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? Message)?.let {
                binding.message = it
            }
        }
    }
}