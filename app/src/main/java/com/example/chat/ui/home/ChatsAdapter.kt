package com.example.chat.ui.home

import android.view.ViewGroup
import com.example.chat.databinding.ItemChatBinding
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.extensions.inflater
import com.example.chat.ui.core.BaseAdapter
import com.example.chat.ui.messages.MessagesAdapter

class ChatsAdapter : MessagesAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.BaseViewHolder {
        val binding = ItemChatBinding.inflate(parent.inflater, parent, false)
        return ChatViewHolder(binding)
    }

    class ChatViewHolder(
        private val binding: ItemChatBinding
    ) : BaseAdapter.BaseViewHolder(binding.root) {

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? MessageEntity)?.let {
                binding.message = it
            }
        }
    }
}