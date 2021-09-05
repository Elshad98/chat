package com.example.chat.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.extensions.longToast
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.presentation.viewmodel.FriendsViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import com.example.chat.ui.core.BaseFragment
import com.example.chat.ui.friends.FriendRequestsFragment
import com.example.chat.ui.friends.FriendsFragment
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.navigation.*

class HomeActivity : BaseActivity() {

    override var fragment: BaseFragment = ChatsFragment()

    override val contentId = R.layout.activity_navigation

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        accountViewModel = viewModel(AccountViewModel::class.java)
        friendsViewModel = viewModel(FriendsViewModel::class.java)

        accountViewModel.accountData.observe(this, Observer(::handleAccount))
        accountViewModel.logoutData.observe(this, Observer(::handleLogout))
        accountViewModel.failureData.observe(
            this,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

        friendsViewModel.addFriendData.observe(this, Observer(::handleAddFriend))
        friendsViewModel.friendRequestsData.observe(this, Observer(::handleFriendRequests))
        friendsViewModel.failureData.observe(
            this,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

        accountViewModel.getAccount()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigation_btn_logout.setOnClickListener {
            accountViewModel.logout()
        }

        navigation_btn_chats.setOnClickListener {
            replaceFragment(ChatsFragment())
            closeDrawer()
        }

        navigation_btn_add_friend.setOnClickListener {
            if (navigation_container_add_friend.visibility == View.VISIBLE) {
                navigation_container_add_friend.visibility = View.GONE
            } else {
                navigation_container_add_friend.visibility = View.VISIBLE
            }
        }

        navigation_btn_add.setOnClickListener {
            hideSoftKeyboard()
            showProgress()
            friendsViewModel.addFriend(navigation_input_email.text.toString())
        }

        navigation_btn_friends.setOnClickListener {
            replaceFragment(FriendsFragment())
            closeDrawer()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navigation_request_container, FriendRequestsFragment())
            .commit()

        navigation_btn_requests.setOnClickListener {
            friendsViewModel.getFriendRequests()

            if (navigation_request_container.visibility == View.VISIBLE) {
                navigation_request_container.visibility = View.GONE
            } else {
                navigation_request_container.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawer_layout.isDrawerOpen(navigation_view)) {
                    drawer_layout.closeDrawer(navigation_view)
                } else {
                    drawer_layout.openDrawer(navigation_view)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(navigation_view)) {
            hideSoftKeyboard()
            drawer_layout.closeDrawer(navigation_view)
        } else {
            super.onBackPressed()
        }
    }

    override fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            Failure.ContactNotFoundError -> showEmailNotFoundDialog()
            else -> super.handleFailure(failure)
        }
    }

    private fun openDrawer() {
        hideSoftKeyboard()
        drawer_layout.openDrawer(navigation_view)
    }

    private fun closeDrawer() {
        hideSoftKeyboard()
        drawer_layout.closeDrawer(navigation_view)
    }

    private fun handleAccount(accountEntity: AccountEntity?) {
        accountEntity?.let {
            navigation_label_user_name.text = it.name
            navigation_label_user_email.text = it.email
            navigation_label_user_status.text = it.status

            navigation_label_user_status.visibility =
                if (it.status.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun handleLogout(none: None?) {
        navigator.showLogin(this)
        finish()
    }

    private fun handleAddFriend(none: None?) {
        navigation_input_email.text.clear()
        navigation_container_add_friend.visibility = View.GONE

        hideProgress()
        longToast(R.string.request_has_been_sent)
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        if (requests?.isEmpty() == true) {
            navigation_request_container.visibility = View.GONE
            if (drawer_layout.isDrawerOpen(navigation_view)) {
                longToast(R.string.no_incoming_invitations)
            }
        }
    }

    private fun showEmailNotFoundDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.message_promt_app))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                navigator.showEmailInvite(this, navigation_input_email.text.toString())
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}