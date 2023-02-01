package com.example.chat.presentation.friend

import android.view.ViewGroup
import com.example.chat.core.extension.inflater
import com.example.chat.core.platform.BaseAdapter
import com.example.chat.databinding.FriendListItemBinding
import com.example.chat.domain.friend.Friend

class FriendListItemAdapter : BaseAdapter<Friend, FriendListItemViewHolder>(FriendDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListItemViewHolder {
        return FriendListItemBinding
            .inflate(parent.inflater, parent, false)
            .let(::FriendListItemViewHolder)
    }
}