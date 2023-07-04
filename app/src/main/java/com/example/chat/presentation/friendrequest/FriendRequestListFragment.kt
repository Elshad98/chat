package com.example.chat.presentation.friendrequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentFriendRequestListBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class FriendRequestListFragment : Fragment(R.layout.fragment_friend_request_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: FriendRequestAdapter
    private val binding by viewBinding(FragmentFriendRequestListBinding::bind)
    private val viewModel: FriendRequestListViewModel by viewModels(factoryProducer = { viewModelFactory })

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
        viewModel.getFriendRequests(true)
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.friendRequests.observe(viewLifecycleOwner, adapter::submitList)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.AlreadyFriendError -> R.string.error_already_friend
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun setupRecyclerView() {
        adapter = FriendRequestAdapter(
            onConfirmClickListener = viewModel::approveFriendRequest,
            onDeclineClickListener = viewModel::cancelFriendRequest
        )
        binding.recyclerView.adapter = adapter
    }
}