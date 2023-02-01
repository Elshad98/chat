package com.example.chat.presentation.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentFriendListBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.App
import javax.inject.Inject

class FriendListFragment : Fragment(R.layout.fragment_friend_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var friendListItemAdapter: FriendListItemAdapter
    private val binding by viewBinding(FragmentFriendListBinding::bind)
    private val viewModel by viewModels<FriendListViewModel>(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.show()
        setupRecyclerView()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFriends()
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.friendList.observe(viewLifecycleOwner, ::handleFriendList)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun handleFriendList(friends: List<Friend>) {
        friendListItemAdapter.submitList(friends)
    }

    private fun setupRecyclerView() {
        friendListItemAdapter = FriendListItemAdapter()
        binding.recyclerView.apply {
            adapter = friendListItemAdapter
        }
    }
}