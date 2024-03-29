package com.example.chat.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.gone
import com.example.chat.core.extension.load
import com.example.chat.core.extension.navigateSafely
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.core.extension.visible
import com.example.chat.databinding.FragmentHomeBinding
import com.example.chat.domain.message.Message
import com.example.chat.domain.user.User
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: ChatAdapter
    private val viewModel by inject<HomeViewModel>()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var textName: TextView
    private lateinit var textEmail: TextView
    private lateinit var textStatus: TextView
    private lateinit var imageUser: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<HomeViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupNavigationDrawer()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser()
        viewModel.getChats()
        viewModel.updateLastSeen()
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner, ::handleUser)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.chatList.observe(viewLifecycleOwner, ::handleChatList)
        viewModel.navigateToLogin.observe(viewLifecycleOwner) { launchLoginFragment() }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> showToast(R.string.error_network)
            is Failure.NoSavedUsersError,
            is Failure.TokenError -> launchLoginFragment()
            else -> showToast(R.string.error_server)
        }
    }

    private fun handleChatList(messages: List<Message>) {
        adapter.submitList(messages)
    }

    private fun handleUser(user: User) {
        textName.text = user.name
        textEmail.text = user.email
        imageUser.load(user.image, R.drawable.user_placeholder)
        if (user.status.isEmpty()) {
            textStatus.gone()
        } else {
            textStatus.visible()
            textStatus.text = user.status
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(
            onChatClickListener = { message ->
                launchMessageListFragment(message.contact.id, message.contact.name)
            }
        )
        binding.recyclerView.adapter = adapter
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
                    R.id.nav_friend_list -> {
                        launchFriendListFragment()
                        drawerLayout.close()
                        true
                    }
                    R.id.nav_invite_friend -> {
                        launchInviteFriendFragment()
                        drawerLayout.close()
                        true
                    }
                    R.id.nav_friend_request_list -> {
                        launchFriendRequestListFragment()
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
        supportActionBar?.hide()
        with(binding) {
            toolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
        }
    }

    private fun launchLoginFragment() {
        findNavController()
            .navigateSafely(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    private fun launchFriendListFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_friendListFragment)
    }

    private fun launchMessageListFragment(contactId: Long, contactName: String) {
        findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToMessageListFragment(contactId, contactName))
    }

    private fun launchSettingsFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
    }

    private fun launchInviteFriendFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_inviteFriendFragment)
    }

    private fun launchFriendRequestListFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_friendRequestListFragment)
    }
}