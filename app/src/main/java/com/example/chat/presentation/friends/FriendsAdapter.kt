package com.example.chat.presentation.friends

import android.view.ViewGroup
import com.example.chat.core.extension.inflater
import com.example.chat.core.platform.BaseAdapter
import com.example.chat.databinding.ItemFriendBinding
import com.example.chat.domain.friend.Friend

class FriendsAdapter : BaseAdapter<Friend, FriendViewHolder>(FriendDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return ItemFriendBinding
            .inflate(parent.inflater, parent, false)
            .let(::FriendViewHolder)
    }
}