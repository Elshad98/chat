package com.example.chat.presentation.friends

import com.example.chat.R
import com.example.chat.core.extension.load
import com.example.chat.core.platform.BaseViewHolder
import com.example.chat.databinding.ItemFriendBinding
import com.example.chat.domain.friend.Friend

class FriendViewHolder(
    private val binding: ItemFriendBinding
) : BaseViewHolder<Friend>(binding.root) {

    override fun bind(item: Friend) {
        with(binding) {
            textName.text = item.name
            imageUser.load(item.image, R.drawable.user_placeholder)
        }
    }
}