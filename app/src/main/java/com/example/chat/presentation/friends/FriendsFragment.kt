package com.example.chat.presentation.friends

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.core.None
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseListFragment
import com.example.chat.presentation.viewmodel.FriendsViewModel

class FriendsFragment : BaseListFragment() {

    override val titleToolbar = R.string.screen_friends
    override val viewAdapter = FriendsAdapter()

    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        friendsViewModel = viewModel(FriendsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendsViewModel.friendsData.observe(viewLifecycleOwner, Observer(::handleFriends))
        friendsViewModel.deleteFriendData.observe(viewLifecycleOwner, Observer(::handleDeleteFriend))
        friendsViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        setOnItemClickListener { item, view ->
            (item as? Friend)?.let { friend ->
                if (view.id == R.id.friend_btn_remove) {
                    showDeleteFriendDialog(friend)
                } else {
                    navigator.showUser(requireActivity(), friend)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showProgress()
        friendsViewModel.getFriends()
    }

    private fun showDeleteFriendDialog(friend: Friend) {
        AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.delete_friend_text, friend.name))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                friendsViewModel.deleteFriend(friend)
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun handleFriends(friends: List<Friend>?) {
        hideProgress()
        if (friends != null && friends.isNotEmpty()) {
            viewAdapter.submitList(friends)
        }
    }

    private fun handleDeleteFriend(none: None?) {
        friendsViewModel.getFriends()
    }
}