package com.example.chat.presentation.friend

import androidx.recyclerview.widget.DiffUtil
import com.example.chat.domain.friend.Friend

class FriendDiffCallback : DiffUtil.ItemCallback<Friend>() {

    override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem == newItem
    }
}