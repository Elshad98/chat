package com.example.chat.ui.friends

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.None
import com.example.chat.presentation.viewmodel.FriendsViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseListFragment

class FriendRequestsFragment : BaseListFragment() {

    override val viewAdapter = FriendRequestsAdapter()

    override val layoutId = R.layout.fragment_friend_requests

    lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendsViewModel = viewModel(FriendsViewModel::class.java)
        friendsViewModel.friendRequestsData.observe(viewLifecycleOwner, Observer(::handleFriendRequests))
        friendsViewModel.approveFriendData.observe(viewLifecycleOwner, Observer(::handleFriendRequestAction))
        friendsViewModel.cancelFriendData.observe(viewLifecycleOwner, Observer(::handleFriendRequestAction))
        friendsViewModel.failureData.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

        setOnItemClickListener { item, view ->
            (item as? FriendEntity)?.let { friend ->
                when (view.id) {
                    R.id.btnApprove -> {
                        showProgress()
                        friendsViewModel.approveFriend(friend)
                    }
                    R.id.btnCancel -> {
                        showProgress()
                        friendsViewModel.cancelFriend(friend)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showProgress()
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        hideProgress()
        requests?.let {
            viewAdapter.clear()
            viewAdapter.add(it)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun handleFriendRequestAction(none: None?) {
        hideProgress()
        friendsViewModel.getFriendRequests()
    }
}