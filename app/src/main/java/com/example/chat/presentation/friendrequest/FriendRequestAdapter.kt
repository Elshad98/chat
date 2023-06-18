package com.example.chat.presentation.friendrequest

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.R
import com.example.chat.core.extension.inflater
import com.example.chat.core.extension.load
import com.example.chat.databinding.ItemFriendRequestBinding
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.friend.FriendDiffCallback

class FriendRequestAdapter(
    private val onConfirmClickListener: (Friend) -> Unit,
    private val onDeclineClickListener: (Friend) -> Unit
) : ListAdapter<Friend, FriendRequestViewHolder>(FriendDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        return ItemFriendRequestBinding
            .inflate(parent.inflater, parent, false)
            .let(::FriendRequestViewHolder)
    }

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        with(holder.binding) {
            val friend = getItem(position)
            textName.text = friend.name
            imageUser.load(friend.image, R.drawable.user_placeholder)
            buttonAdd.setOnClickListener {
                onConfirmClickListener(friend)
            }
            buttonDecline.setOnClickListener {
                onDeclineClickListener(friend)
            }
        }
    }
}