package com.example.chat.presentation.message

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.core.extension.inflater
import com.example.chat.databinding.ItemMessageImageBinding
import com.example.chat.databinding.ItemMessagePlainTextBinding
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageType

class MessageAdapter(
    private val onMessageClickListener: OnMessageClickListener
) : ListAdapter<Message, BaseMessageViewHolder>(MessageDiffCallback()) {

    companion object {

        private const val TYPE_IMAGE = 0
        private const val TYPE_PLAIN_TEXT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> {
                val binding = ItemMessageImageBinding.inflate(parent.inflater)
                MessageImageViewHolder(binding, onMessageClickListener)
            }
            TYPE_PLAIN_TEXT -> {
                val binding = ItemMessagePlainTextBinding.inflate(parent.inflater)
                MessagePlainTextViewHolder(binding, onMessageClickListener)
            }
            else -> throw IllegalArgumentException("Unhandled view type ($viewType)")
        }
    }

    override fun onBindViewHolder(holder: BaseMessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return when (message.type) {
            MessageType.IMAGE -> TYPE_IMAGE
            MessageType.TEXT -> TYPE_PLAIN_TEXT
        }
    }
}