package com.example.chat.presentation.invitefriend

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentInviteFriendBinding
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class InviteFriendFragment : Fragment(R.layout.fragment_invite_friend) {

    private val viewModel by inject<InviteFriendViewModel>()
    private val binding by viewBinding(FragmentInviteFriendBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<InviteFriendViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupClickListener()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListener()
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.navigateToHome.observe(viewLifecycleOwner) {
            showToast(R.string.request_has_been_sent)
            findNavController().popBackStack()
        }
        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            binding.inputLayoutEmail.isErrorEnabled = it
            binding.inputLayoutEmail.error = if (it) {
                getString(R.string.error_invalid_email)
            } else {
                null
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            is Failure.AlreadyFriendError -> R.string.error_already_friend
            is Failure.ContactNotFoundError -> R.string.error_contact_not_found
            is Failure.AlreadyRequestedFriendError -> R.string.error_already_requested_friend
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun setupClickListener() {
        with(binding) {
            buttonSendInvite.setOnClickListener {
                viewModel.addFriend(inputEmail.trimmedText)
            }
        }
    }

    private fun addTextChangeListener() {
        binding.inputEmail.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputEmail()
        }
    }
}