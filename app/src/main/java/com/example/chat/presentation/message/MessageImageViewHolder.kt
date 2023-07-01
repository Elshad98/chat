package com.example.chat.presentation.message

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.chat.R
import com.example.chat.core.extension.load
import com.example.chat.databinding.ItemMessageImageBinding
import com.example.chat.domain.message.Message

class MessageImageViewHolder(
    private val binding: ItemMessageImageBinding
) : BaseMessageViewHolder(binding.root) {

    override fun bind(message: Message) {
        with(binding) {
            val resId = if (message.fromMe) {
                R.drawable.shape_message_background_user
            } else {
                R.drawable.shape_message_background_other
            }
            container.setBackgroundResource(resId)
            imagePhoto.load(message.message, R.drawable.user_placeholder)
            container.updateLayoutParams<ConstraintLayout.LayoutParams> {
                horizontalBias = if (message.fromMe) 1f else 0f
            }
        }
    }
}