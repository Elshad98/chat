package com.example.chat.ui.friends

import android.view.View
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.ui.core.BaseAdapter
import kotlinx.android.synthetic.main.item_friend_request.view.*

class FriendRequestsAdapter : BaseAdapter<FriendRequestsAdapter.FriendRequestViewHolder>() {

    override val layoutRes = R.layout.item_friend_request

    override fun createHolder(view: View, viewType: Int): FriendRequestViewHolder {
        return FriendRequestViewHolder(view)
    }

    class FriendRequestViewHolder(view: View) : BaseViewHolder(view) {

        init {
            view.friend_btn_approve.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
            view.friend_btn_cancel.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
        }

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let { friend ->
                view.friend_label_name.text = friend.name
            }
        }
    }
}