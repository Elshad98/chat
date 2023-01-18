package com.example.chat.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.gone
import com.example.chat.core.extension.load
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentHomeBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.domain.user.User
import com.example.chat.presentation.App
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>(factoryProducer = { viewModelFactory })

    private lateinit var textName: TextView
    private lateinit var textEmail: TextView
    private lateinit var textStatus: TextView
    private lateinit var imageUser: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        setupToolbar()
        setupNavigationDrawer()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner, ::handleUser)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.navigateToLogin.observe(viewLifecycleOwner) { launchLoginFragment() }
    }

    private fun handleFailure(failure: Failure) {
        showToast(getString(R.string.error_server))
    }

    private fun handleUser(user: User) {
        textName.text = user.name
        textEmail.text = user.email
        imageUser.load(user.photo, R.drawable.ic_avatar_placeholder)
        if (user.status.isEmpty()) {
            textStatus.gone()
        } else {
            textStatus.text = user.status
        }
    }

    private fun setupNavigationDrawer() {
        val header = binding.navigationView.getHeaderView(0)
        textName = header.findViewById(R.id.text_name)
        imageUser = header.findViewById(R.id.image_user)
        textEmail = header.findViewById(R.id.text_email)
        textStatus = header.findViewById(R.id.text_status)

        header.setOnClickListener {
            launchSettingsFragment()
            binding.drawerLayout.close()
        }

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
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
                    viewModel.logout()
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

    private fun launchLoginFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun launchFriendsFragment() = Unit

    private fun launchSettingsFragment() = Unit

    private fun launchInviteFriendFragment() = Unit

    private fun launchFriendRequestsFragment() = Unit
}