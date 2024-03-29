package com.example.chat.presentation.friend

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.R
import com.example.chat.core.extension.inflater
import com.example.chat.core.extension.load
import com.example.chat.databinding.ItemFriendBinding
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.extension.getLastSeenText

class FriendAdapter(
    private val onFriendClickListener: (Friend) -> Unit,
    private val onMessageClickListener: (Friend) -> Unit
) : ListAdapter<Friend, FriendViewHolder>(FriendDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return ItemFriendBinding
            .inflate(parent.inflater, parent, false)
            .let(::FriendViewHolder)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        with(holder.binding) {
            val friend = getItem(position)
            root.setOnClickListener {
                onFriendClickListener.invoke(friend)
            }
            buttonMessage.setOnClickListener {
                onMessageClickListener.invoke(friend)
            }
            textName.text = friend.name
            textLastSeen.text = friend.getLastSeenText(root.context)
            imageUser.load(friend.image, R.drawable.user_placeholder)
        }
    }
}