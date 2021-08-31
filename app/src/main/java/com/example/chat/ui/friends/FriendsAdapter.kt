package com.example.chat.ui.friends

import android.view.View
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.ui.core.BaseAdapter
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

    override val layoutRes = R.layout.item_friend

    override fun createHolder(view: View, viewType: Int): FriendViewHolder {
        return FriendViewHolder(view)
    }

    class FriendViewHolder(view: View) : BaseViewHolder(view) {

        init {
            view.btnRemove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }
        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let { friend ->
                view.tvName.text = friend.name
                view.tvStatus.text = friend.status
            }
        }
    }
}