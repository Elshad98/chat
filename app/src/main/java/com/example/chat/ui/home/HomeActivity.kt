package com.example.chat.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.databinding.ActivityNavigationBinding
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.extensions.gone
import com.example.chat.extensions.hideKeyboard
import com.example.chat.extensions.showToast
import com.example.chat.extensions.toggleVisibility
import com.example.chat.extensions.visible
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.presentation.viewmodel.FriendsViewModel
import com.example.chat.remote.service.AccountService
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import com.example.chat.ui.core.BaseFragment
import com.example.chat.ui.firebase.NotificationHelper
import com.example.chat.ui.friends.FriendRequestsFragment
import com.example.chat.ui.friends.FriendsFragment

class HomeActivity : BaseActivity() {

    override var fragment: BaseFragment = ChatsFragment()
    override val contentId = R.layout.activity_navigation

    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(layoutInflater)
    }
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        accountViewModel = viewModel(AccountViewModel::class.java)
        friendsViewModel = viewModel(FriendsViewModel::class.java)

        accountViewModel.accountData.observe(this, Observer(::handleAccount))
        accountViewModel.logoutData.observe(this, Observer(::handleLogout))
        accountViewModel.failureData.observe(this, Observer(::handleFailure))

        friendsViewModel.addFriendData.observe(this, Observer(::handleAddFriend))
        friendsViewModel.friendRequestsData.observe(this, Observer(::handleFriendRequests))
        friendsViewModel.failureData.observe(this, Observer(::handleFailure))

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigation.buttonLogout.setOnClickListener {
            accountViewModel.logout()
        }

        binding.navigation.buttonChats.setOnClickListener {
            replaceFragment(ChatsFragment())
            closeDrawer()
        }

        binding.navigation.buttonAddFriend.setOnClickListener {
            binding.navigation.containerAddFriend.toggleVisibility()
        }

        binding.navigation.buttonAdd.setOnClickListener {
            hideKeyboard()
            showProgress()
            friendsViewModel.addFriend(binding.navigation.inputEmail.text.toString())
        }

        binding.navigation.buttonFriends.setOnClickListener {
            replaceFragment(FriendsFragment())
            closeDrawer()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_request, FriendRequestsFragment())
            .commit()

        binding.navigation.buttonRequests.setOnClickListener {
            friendsViewModel.getFriendRequests(true)

            binding.navigation.containerRequest.toggleVisibility()
        }

        when (intent.getStringExtra("type")) {
            NotificationHelper.TYPE_ADD_FRIEND -> {
                openDrawer()
                friendsViewModel.getFriendRequests()
                binding.navigation.containerRequest.visible()
            }
            NotificationHelper.TYPE_SEND_MESSAGE -> {
                val contactId = intent.getLongExtra(AccountService.PARAM_CONTACT_ID, 0)
                val contactName = intent.getStringExtra(AccountService.PARAM_NAME).orEmpty()
                navigator.showChatWithContact(this, contactId, contactName)
            }
        }

        binding.navigation.containerProfile.setOnClickListener {
            navigator.showAccount(this)
            Handler(Looper.getMainLooper()).postDelayed({ closeDrawer() }, 200)
        }
    }

    override fun onResume() {
        super.onResume()
        accountViewModel.getAccount()
        accountViewModel.updateLastSeen()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (binding.drawerLayout.isDrawerOpen(binding.navigation.navigationView)) {
                    binding.drawerLayout.closeDrawer(binding.navigation.navigationView)
                } else {
                    binding.drawerLayout.openDrawer(binding.navigation.navigationView)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.navigation.navigationView)) {
            hideKeyboard()
            binding.drawerLayout.closeDrawer(binding.navigation.navigationView)
        } else {
            super.onBackPressed()
        }
    }

    override fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            Failure.ContactNotFoundError -> navigator.showEmailNotFoundDialog(
                this,
                binding.navigation.inputEmail.text.toString()
            )
            else -> super.handleFailure(failure)
        }
    }

    override fun setupContent() {
        setContentView(binding.root)
    }

    private fun openDrawer() {
        hideKeyboard()
        binding.drawerLayout.openDrawer(binding.navigation.navigationView)
    }

    private fun closeDrawer() {
        hideKeyboard()
        binding.drawerLayout.closeDrawer(binding.navigation.navigationView)
    }

    private fun handleAccount(accountEntity: AccountEntity?) {
        accountEntity?.let(binding.navigation::setAccount)
    }

    private fun handleLogout(none: None?) {
        navigator.showLogin(this)
        finish()
    }

    private fun handleAddFriend(none: None?) {
        binding.navigation.inputEmail.text.clear()
        binding.navigation.containerAddFriend.gone()

        hideProgress()
        showToast(R.string.request_has_been_sent)
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        if (requests?.isEmpty() == true) {
            binding.navigation.containerRequest.gone()
            if (binding.drawerLayout.isDrawerOpen(binding.navigation.navigationView)) {
                showToast(R.string.no_incoming_invites)
            }
        }
    }
}