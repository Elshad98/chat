package com.example.chat.ui.friends

import android.view.View
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.extensions.toggleVisibility
import com.example.chat.ui.core.BaseAdapter
import com.example.chat.ui.core.GlideHelper
import kotlinx.android.synthetic.main.item_friend.view.friend_btn_remove
import kotlinx.android.synthetic.main.item_friend.view.friend_label_name
import kotlinx.android.synthetic.main.item_friend.view.friend_label_status
import kotlinx.android.synthetic.main.item_friend_request.view.friend_img_photo

class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

    override val layoutRes = R.layout.item_friend

    override fun createHolder(view: View, viewType: Int): FriendViewHolder {
        return FriendViewHolder(view)
    }

    class FriendViewHolder(view: View) : BaseViewHolder(view) {

        init {
            view.friend_btn_remove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }
        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let { friend ->
                GlideHelper.loadImage(view.context, friend.image, view.friend_img_photo, R.drawable.ic_account_circle)
                view.friend_label_name.text = friend.name
                view.friend_label_status.text = friend.status

                view.friend_label_status.toggleVisibility(friend.status.isNotEmpty())
            }
        }
    }
}