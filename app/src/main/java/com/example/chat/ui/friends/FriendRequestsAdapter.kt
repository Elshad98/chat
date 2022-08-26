package com.example.chat.ui.friends

import android.view.ViewGroup
import com.example.chat.databinding.ItemFriendRequestBinding
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.extensions.inflater
import com.example.chat.ui.core.BaseAdapter

class FriendRequestsAdapter : BaseAdapter<FriendEntity, FriendRequestsAdapter.FriendRequestViewHolder>(
    FriendsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val binding = ItemFriendRequestBinding.inflate(parent.inflater, parent, false)
        return FriendRequestViewHolder(binding)
    }

    class FriendRequestViewHolder(
        private val binding: ItemFriendRequestBinding
    ) : BaseViewHolder(binding.root) {

        init {
            binding.friendBtnApprove.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
            binding.friendBtnCancel.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
        }

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let(binding::setFriend)
        }
    }
}