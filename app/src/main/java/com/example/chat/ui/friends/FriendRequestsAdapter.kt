package com.example.chat.ui.friends

import android.view.View
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.ui.core.BaseAdapter
import com.example.chat.ui.core.GlideHelper
import kotlinx.android.synthetic.main.item_friend_request.view.friend_btn_approve
import kotlinx.android.synthetic.main.item_friend_request.view.friend_btn_cancel
import kotlinx.android.synthetic.main.item_friend_request.view.friend_img_photo
import kotlinx.android.synthetic.main.item_friend_request.view.friend_label_name

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
                GlideHelper.loadImage(
                    view.context,
                    friend.image,
                    view.friend_img_photo,
                    R.drawable.ic_account_circle
                )
                view.friend_label_name.text = friend.name
            }
        }
    }
}