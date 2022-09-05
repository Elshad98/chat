package com.example.chat.ui.friends

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.None
import com.example.chat.presentation.viewmodel.FriendsViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseListFragment

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
            (item as? FriendEntity)?.let { friend ->
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

    private fun showDeleteFriendDialog(friend: FriendEntity) {
        AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.delete_friend))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                friendsViewModel.deleteFriend(friend)
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun handleFriends(friends: List<FriendEntity>?) {
        hideProgress()
        if (friends != null && friends.isNotEmpty()) {
            viewAdapter.submitList(friends)
        }
    }

    private fun handleDeleteFriend(none: None?) {
        friendsViewModel.getFriends()
    }
}