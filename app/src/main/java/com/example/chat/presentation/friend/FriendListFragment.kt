package com.example.chat.presentation.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentFriendListBinding
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class FriendListFragment : Fragment(R.layout.fragment_friend_list), FriendDialogFragment.OnMessageClickListener {

    private lateinit var adapter: FriendAdapter
    private val viewModel by inject<FriendListViewModel>()
    private val binding by viewBinding(FragmentFriendListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<FriendListViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupClickListener()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFriends()
    }

    override fun onMessageClick(friend: Friend) {
        launchMessageListFragment(friend.id, friend.name)
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun setupClickListener() {
        binding.buttonInviteFriend.setOnClickListener {
            launchInviteFriendFragment()
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.friendList.observe(viewLifecycleOwner, ::handleFriendList)
        viewModel.removedFriend.observe(viewLifecycleOwner) { friend ->
            showToast(getString(R.string.friend_remove_success, friend.name))
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun handleFriendList(friends: List<Friend>) {
        adapter.submitList(friends)
    }

    private fun setupRecyclerView() {
        adapter = FriendAdapter(
            onFriendClickListener = { friend ->
                showFriendDialogFragment(friend)
            },
            onMessageClickListener = { friend ->
                launchMessageListFragment(friend.id, friend.name)
            }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun showFriendDialogFragment(friend: Friend) {
        FriendDialogFragment.newInstance(friend).apply {
            @Suppress("DEPRECATION")
            setTargetFragment(this@FriendListFragment, 0)
            show(this@FriendListFragment.parentFragmentManager, FriendDialogFragment.TAG)
        }
    }

    private fun launchMessageListFragment(contactId: Long, contactName: String) {
        findNavController()
            .navigate(FriendListFragmentDirections.actionFriendListFragmentToMessageListFragment(contactId, contactName))
    }

    private fun launchInviteFriendFragment() {
        findNavController().navigate(R.id.action_friendListFragment_to_inviteFriendFragment)
    }
}