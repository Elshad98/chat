package com.example.chat.presentation.message

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.chat.R
import com.example.chat.core.extension.load
import com.example.chat.databinding.ItemMessageImageBinding
import com.example.chat.domain.message.Message
import com.example.chat.presentation.extension.getDateText

class MessageImageViewHolder(
    private val binding: ItemMessageImageBinding,
    private val onMessageClickListener: OnMessageClickListener
) : BaseMessageViewHolder(binding.root) {

    override fun bind(message: Message) {
        with(binding) {
            val resId = if (message.fromMe) {
                R.drawable.shape_message_background_user
            } else {
                R.drawable.shape_message_background_other
            }
            textMessageTime.text = message.getDateText(root.context)
            textMessageTime.updateLayoutParams<ConstraintLayout.LayoutParams> {
                horizontalBias = if (message.fromMe) 1f else 0f
            }
            container.setBackgroundResource(resId)
            imagePhoto.load(message.message, R.drawable.picture_placeholder)
            container.updateLayoutParams<ConstraintLayout.LayoutParams> {
                horizontalBias = if (message.fromMe) 1f else 0f
            }
            root.setOnClickListener {
                onMessageClickListener.onMessageClick(message)
            }
            root.setOnLongClickListener {
                onMessageClickListener.onMessageLongClick(message)
                true
            }
        }
    }
}