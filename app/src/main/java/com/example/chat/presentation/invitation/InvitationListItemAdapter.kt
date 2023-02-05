package com.example.chat.presentation.invitation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.R
import com.example.chat.core.extension.inflater
import com.example.chat.core.extension.load
import com.example.chat.databinding.InvitationListItemBinding
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.friend.FriendDiffCallback

class InvitationListItemAdapter(
    private val onConfirmClickListener: (Friend) -> Unit,
    private val onDeclineClickListener: (Friend) -> Unit
) : ListAdapter<Friend, InvitationListItemViewHolder>(
    FriendDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvitationListItemViewHolder {
        return InvitationListItemBinding
            .inflate(parent.inflater, parent, false)
            .let(::InvitationListItemViewHolder)
    }

    override fun onBindViewHolder(holder: InvitationListItemViewHolder, position: Int) {
        with(holder.binding) {
            val friend = getItem(position)
            textName.text = friend.name
            imageUser.load(friend.photo, R.drawable.user_placeholder)
            buttonAdd.setOnClickListener {
                onConfirmClickListener(friend)
            }
            buttonDecline.setOnClickListener {
                onDeclineClickListener(friend)
            }
        }
    }
}