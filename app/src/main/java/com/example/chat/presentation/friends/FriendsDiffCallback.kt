package com.example.chat.presentation.friends

import androidx.recyclerview.widget.DiffUtil
import com.example.chat.domain.friends.FriendEntity

class FriendsDiffCallback : DiffUtil.ItemCallback<FriendEntity>() {

    override fun areItemsTheSame(oldItem: FriendEntity, newItem: FriendEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FriendEntity, newItem: FriendEntity): Boolean {
        return oldItem == newItem
    }
}