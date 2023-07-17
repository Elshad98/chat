package com.example.chat.presentation.message

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.chat.R
import com.example.chat.databinding.ItemMessagePlainTextBinding
import com.example.chat.domain.message.Message
import com.example.chat.presentation.extension.getDateText

class MessagePlainTextViewHolder(
    private val binding: ItemMessagePlainTextBinding
) : BaseMessageViewHolder(binding.root) {

    override fun bind(message: Message) {
        with(binding) {
            val resId = if (message.fromMe) {
                R.drawable.shape_message_background_user
            } else {
                R.drawable.shape_message_background_other
            }
            textMessage.text = message.message
            textMessageTime.text = message.getDateText(root.context)
            textMessageTime.updateLayoutParams<ConstraintLayout.LayoutParams> {
                horizontalBias = if (message.fromMe) 1f else 0f
            }
            container.setBackgroundResource(resId)
            container.updateLayoutParams<ConstraintLayout.LayoutParams> {
                horizontalBias = if (message.fromMe) 1f else 0f
            }
        }
    }
}