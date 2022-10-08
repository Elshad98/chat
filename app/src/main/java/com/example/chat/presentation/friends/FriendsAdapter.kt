package com.example.chat.presentation.friends

import android.view.ViewGroup
import com.example.chat.core.extension.inflater
import com.example.chat.databinding.ItemFriendBinding
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.core.BaseAdapter

class FriendsAdapter : BaseAdapter<Friend, FriendsAdapter.FriendViewHolder>(
    FriendDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(parent.inflater, parent, false)
        return FriendViewHolder(binding)
    }

    class FriendViewHolder(
        private val binding: ItemFriendBinding
    ) : BaseViewHolder(binding.root) {

        init {
            binding.friendBtnRemove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? Friend)?.let(binding::setFriend)
        }
    }
}