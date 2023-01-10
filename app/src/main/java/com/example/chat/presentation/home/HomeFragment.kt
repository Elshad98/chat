package com.example.chat.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.hide()
        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_friends -> {
                    launchFriendsFragment()
                    true
                }
                R.id.nav_invite_friend -> {
                    launchInviteFriendFragment()
                    true
                }
                R.id.nav_friend_requests -> {
                    launchFriendRequestsFragment()
                    true
                }
                R.id.nav_settings -> {
                    launchSettingsFragment()
                    true
                }
                R.id.nav_logout -> {
                    showToast("logout")
                    true
                }
                else -> false
            }
        }
    }

    private fun launchFriendsFragment() = Unit

    private fun launchSettingsFragment() = Unit

    private fun launchInviteFriendFragment() = Unit

    private fun launchFriendRequestsFragment() = Unit
}