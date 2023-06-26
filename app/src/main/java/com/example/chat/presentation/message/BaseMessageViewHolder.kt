package com.example.chat.presentation.message

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.domain.message.Message

abstract class BaseMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(message: Message)
}