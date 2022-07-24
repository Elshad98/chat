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

    lateinit var viewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel(FriendsViewModel::class.java)
        viewModel.friendsData.observe(viewLifecycleOwner, Observer(::handleFriends))
        viewModel.deleteFriendData.observe(viewLifecycleOwner, Observer(::handleDeleteFriend))
        viewModel.failureData.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

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
        viewModel.getFriends()
    }

    private fun showDeleteFriendDialog(friend: FriendEntity) {
        activity?.let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.delete_friend))
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    viewModel.deleteFriend(friend)
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    private fun handleFriends(friends: List<FriendEntity>?) {
        hideProgress()
        if (friends != null) {
            viewAdapter.clear()
            viewAdapter.addItems(friends)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun handleDeleteFriend(none: None?) {
        viewModel.getFriends()
    }
}