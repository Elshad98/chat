package com.example.chat.presentation.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentFriendsBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.App
import javax.inject.Inject

class FriendsFragment : Fragment(R.layout.fragment_friends) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: FriendsAdapter
    private val binding by viewBinding(FragmentFriendsBinding::bind)
    private val viewModel: FriendsViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFriends()
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(true)
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
        adapter = FriendsAdapter(
            onFriendClickListener = { friend ->
                FriendDialogFragment.newInstance(friend).apply {
                    setOnMessageClickListener(
                        object : FriendDialogFragment.OnMessageClickListener {

                            override fun onClick(friend: Friend) {
                                launchMessagesFragment(friend.friendsId, friend.name)
                            }
                        }
                    )
                }.show(parentFragmentManager, FriendDialogFragment.TAG)
            }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun launchMessagesFragment(contactId: Long, contactName: String) {
        findNavController()
            .navigate(FriendsFragmentDirections.actionFriendsFragmentToMessageListFragment(contactId, contactName))
    }
}