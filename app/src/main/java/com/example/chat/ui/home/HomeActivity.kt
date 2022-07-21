package com.example.chat.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.extensions.gone
import com.example.chat.extensions.longToast
import com.example.chat.extensions.toggleVisibility
import com.example.chat.extensions.visible
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.presentation.viewmodel.FriendsViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import com.example.chat.ui.core.BaseFragment
import com.example.chat.ui.firebase.NotificationHelper
import com.example.chat.ui.friends.FriendRequestsFragment
import com.example.chat.ui.friends.FriendsFragment
import kotlinx.android.synthetic.main.activity_navigation.drawer_layout
import kotlinx.android.synthetic.main.navigation.navigation_btn_add
import kotlinx.android.synthetic.main.navigation.navigation_btn_add_friend
import kotlinx.android.synthetic.main.navigation.navigation_btn_chats
import kotlinx.android.synthetic.main.navigation.navigation_btn_friends
import kotlinx.android.synthetic.main.navigation.navigation_btn_logout
import kotlinx.android.synthetic.main.navigation.navigation_btn_requests
import kotlinx.android.synthetic.main.navigation.navigation_container_add_friend
import kotlinx.android.synthetic.main.navigation.navigation_input_email
import kotlinx.android.synthetic.main.navigation.navigation_label_user_email
import kotlinx.android.synthetic.main.navigation.navigation_label_user_name
import kotlinx.android.synthetic.main.navigation.navigation_label_user_status
import kotlinx.android.synthetic.main.navigation.navigation_profile_container
import kotlinx.android.synthetic.main.navigation.navigation_request_container
import kotlinx.android.synthetic.main.navigation.navigation_view

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
            navigation_container_add_friend.toggleVisibility()
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
            friendsViewModel.getFriendRequests(true)

            navigation_request_container.toggleVisibility()
        }

        when (intent.getStringExtra("type")) {
            NotificationHelper.TYPE_ADD_FRIEND -> {
                openDrawer()
                friendsViewModel.getFriendRequests()
                navigation_request_container.visible()
            }
        }

        navigation_profile_container.setOnClickListener {
            navigator.showAccount(this)
            Handler(Looper.getMainLooper()).postDelayed({ closeDrawer() }, 200)
        }
    }

    override fun onResume() {
        super.onResume()
        accountViewModel.getAccount()
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
            Failure.ContactNotFoundError -> navigator.showEmailNotFoundDialog(
                this,
                navigation_input_email.text.toString()
            )
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

            navigation_label_user_status.toggleVisibility(it.status.isNotEmpty())
        }
    }

    private fun handleLogout(none: None?) {
        navigator.showLogin(this)
        finish()
    }

    private fun handleAddFriend(none: None?) {
        navigation_input_email.text.clear()
        navigation_container_add_friend.gone()

        hideProgress()
        longToast(R.string.request_has_been_sent)
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        if (requests?.isEmpty() == true) {
            navigation_request_container.gone()
            if (drawer_layout.isDrawerOpen(navigation_view)) {
                longToast(R.string.no_incoming_invitations)
            }
        }
    }
}