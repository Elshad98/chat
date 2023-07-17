package com.example.chat.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.R
import com.example.chat.core.extension.inflater
import com.example.chat.core.extension.load
import com.example.chat.databinding.ItemChatBinding
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageType
import com.example.chat.presentation.extension.getDateText
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
            val item = getItem(position)
            root.setOnClickListener {
                onChatClickListener.invoke(item)
            }
            textLastMessage.text = when (item.type) {
                MessageType.TEXT -> {
                    if (item.fromMe) {
                        root.context.getString(R.string.chat_list_you, item.message)
                    } else {
                        item.message
                    }
                }
                MessageType.IMAGE -> {
                    val photo = root.context.getString(R.string.chat_list_photo)
                    if (item.fromMe) {
                        root.context.getString(R.string.chat_list_you, photo)
                    } else {
                        photo
                    }
                }
            }
            textName.text = item.contact.name
            textLastMessageTime.text = item.getDateText(root.context)
            imageUser.load(item.contact.image, R.drawable.user_placeholder)
        }
    }
}