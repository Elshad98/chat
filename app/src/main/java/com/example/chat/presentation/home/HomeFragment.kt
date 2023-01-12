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
        setupToolbar()
        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_friends -> {
                    launchFriendsFragment()
                    binding.drawerLayout.close()
                    true
                }
                R.id.nav_invite_friend -> {
                    launchInviteFriendFragment()
                    binding.drawerLayout.close()
                    true
                }
                R.id.nav_friend_requests -> {
                    launchFriendRequestsFragment()
                    binding.drawerLayout.close()
                    true
                }
                R.id.nav_settings -> {
                    launchSettingsFragment()
                    binding.drawerLayout.close()
                    true
                }
                R.id.nav_logout -> {
                    showToast("logout")
                    binding.drawerLayout.close()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupToolbar() {
        with(binding) {
            supportActionBar?.hide()
            toolbar.title = getString(R.string.app_name)
            toolbar.setNavigationIcon(R.drawable.ic_menu)
            toolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
        }
    }

    private fun launchFriendsFragment() = Unit

    private fun launchSettingsFragment() = Unit

    private fun launchInviteFriendFragment() = Unit

    private fun launchFriendRequestsFragment() = Unit
}