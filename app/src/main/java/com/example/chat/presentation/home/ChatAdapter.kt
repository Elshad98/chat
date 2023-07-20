package com.example.chat.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.R
import com.example.chat.core.extension.inflater
import com.example.chat.core.extension.load
import com.example.chat.databinding.ItemChatBinding
import com.example.chat.domain.message.Message
import com.example.chat.presentation.extension.getDateText
import com.example.chat.presentation.extension.messagePreview
import com.example.chat.presentation.message.MessageDiffCallback

class ChatAdapter(
    private val onChatClickListener: (Message) -> Unit
) : ListAdapter<Message, ChatViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ItemChatBinding
            .inflate(parent.inflater, parent, false)
            .let(::ChatViewHolder)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        with(holder.binding) {
            val message = getItem(position)
            root.setOnClickListener {
                onChatClickListener.invoke(message)
            }
            textName.text = message.contact.name
            textLastMessage.text = message.messagePreview(root.context)
            textLastMessageTime.text = message.getDateText(root.context)
            imageUser.load(message.contact.image, R.drawable.user_placeholder)
        }
    }
}