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
import com.example.chat.domain.message.Message
import com.example.chat.domain.user.User
import com.example.chat.presentation.App
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var chatListItemAdapter: ChatListItemAdapter
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
        setupToolbar()
        setupRecyclerView()
        setupClickListener()
        setupNavigationDrawer()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser()
        viewModel.getChats()
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner, ::handleUser)
        viewModel.chatList.observe(viewLifecycleOwner, ::handleChats)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.navigateToLogin.observe(viewLifecycleOwner) { launchLoginFragment() }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> showToast(R.string.error_network)
            else -> showToast(R.string.error_server)
        }
    }

    private fun handleChats(messages: List<Message>) {
        chatListItemAdapter.submitList(messages)
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

    private fun setupRecyclerView() {
        chatListItemAdapter = ChatListItemAdapter()
        binding.recyclerView.apply {
            adapter = chatListItemAdapter
        }
    }

    private fun setupClickListener() {
        chatListItemAdapter.onItemClickListener = {
            launchMessagesFragment()
        }
    }

    private fun setupNavigationDrawer() {
        with(binding) {
            val header = navigationView.getHeaderView(0)
            textName = header.findViewById(R.id.text_name)
            imageUser = header.findViewById(R.id.image_user)
            textEmail = header.findViewById(R.id.text_email)
            textStatus = header.findViewById(R.id.text_status)

            header.setOnClickListener {
                launchSettingsFragment()
                drawerLayout.close()
            }

            navigationView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_friends -> {
                        launchFriendsFragment()
                        drawerLayout.close()
                        true
                    }
                    R.id.nav_invite_friend -> {
                        launchInviteFriendFragment()
                        drawerLayout.close()
                        true
                    }
                    R.id.nav_friend_requests -> {
                        launchFriendRequestsFragment()
                        drawerLayout.close()
                        true
                    }
                    R.id.nav_settings -> {
                        launchSettingsFragment()
                        drawerLayout.close()
                        true
                    }
                    R.id.nav_logout -> {
                        viewModel.logout()
                        drawerLayout.close()
                        true
                    }
                    else -> false
                }
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

    private fun launchMessagesFragment() = Unit

    private fun launchSettingsFragment() = Unit

    private fun launchInviteFriendFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_inviteFriendFragment)
    }

    private fun launchFriendRequestsFragment() = Unit
}