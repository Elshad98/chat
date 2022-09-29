package com.example.chat.presentation.friends

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.None
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseListFragment
import com.example.chat.presentation.viewmodel.FriendsViewModel

class FriendRequestsFragment : BaseListFragment() {

    override val viewAdapter = FriendRequestsAdapter()
    override val layoutId = R.layout.fragment_friend_requests

    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        friendsViewModel = viewModel(FriendsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendsViewModel.friendRequestsData.observe(viewLifecycleOwner, Observer(::handleFriendRequests))
        friendsViewModel.approveFriendData.observe(viewLifecycleOwner, Observer(::handleFriendRequestAction))
        friendsViewModel.cancelFriendData.observe(viewLifecycleOwner, Observer(::handleFriendRequestAction))
        friendsViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        setOnItemClickListener { item, view ->
            (item as? FriendEntity)?.let { friend ->
                when (view.id) {
                    R.id.friend_btn_approve -> {
                        showProgress()
                        friendsViewModel.approveFriend(friend)
                    }
                    R.id.friend_btn_cancel -> {
                        showProgress()
                        friendsViewModel.cancelFriend(friend)
                    }
                    else -> navigator.showUser(requireActivity(), friend)
                }
            }
        }
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        hideProgress()
        if (requests != null && requests.isNotEmpty()) {
            viewAdapter.submitList(requests)
        }
    }

    private fun handleFriendRequestAction(none: None?) {
        hideProgress()
        friendsViewModel.getFriendRequests()
    }
}