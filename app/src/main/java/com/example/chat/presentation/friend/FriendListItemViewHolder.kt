package com.example.chat.presentation.friend

import com.example.chat.R
import com.example.chat.core.extension.load
import com.example.chat.core.platform.BaseViewHolder
import com.example.chat.databinding.FriendListItemBinding
import com.example.chat.domain.friend.Friend

class FriendListItemViewHolder(
    private val binding: FriendListItemBinding
) : BaseViewHolder<Friend>(binding.root) {

    override fun bind(item: Friend) {
        with(binding) {
            imageUser.load(item.photo, R.drawable.user_placeholder)
            textName.text = item.name
        }
    }
}