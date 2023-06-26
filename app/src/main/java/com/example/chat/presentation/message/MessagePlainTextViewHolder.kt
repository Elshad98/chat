package com.example.chat.presentation.message

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.chat.R
import com.example.chat.databinding.ItemMessagePlainTextBinding
import com.example.chat.domain.message.Message

class MessagePlainTextViewHolder(
    private val binding: ItemMessagePlainTextBinding
) : BaseMessageViewHolder(binding.root) {

    override fun bind(message: Message) {
        with(binding) {
            container.apply {
                updateLayoutParams<ConstraintLayout.LayoutParams> {
                    horizontalBias = if (message.fromMe) 1f else 0f
                }
                val resId = if (message.fromMe) {
                    R.drawable.shape_message_background_user
                } else {
                    R.drawable.shape_message_background_other
                }
                setBackgroundResource(resId)
            }
            textMessage.text = message.message
        }
    }
}