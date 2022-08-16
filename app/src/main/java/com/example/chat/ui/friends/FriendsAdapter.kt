package com.example.chat.ui.friends

import android.view.ViewGroup
import com.example.chat.databinding.ItemFriendBinding
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.extensions.inflater
import com.example.chat.ui.core.BaseAdapter

class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

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
            (item as? FriendEntity)?.let(binding::setFriend)
        }
    }
}