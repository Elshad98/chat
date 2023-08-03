package com.example.chat.presentation.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.gone
import com.example.chat.core.extension.load
import com.example.chat.core.extension.showToast
import com.example.chat.databinding.DialogFriendBinding
import com.example.chat.domain.friend.Friend
import com.example.chat.presentation.extension.getLastSeenText
import com.example.chat.presentation.extension.installVMBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class FriendDialogFragment : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "FriendDialogFragment"
        private const val ARG_FRIEND = "friend"

        fun newInstance(friend: Friend): FriendDialogFragment {
            return FriendDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_FRIEND, friend)
                }
            }
        }
    }

    private val viewModel by inject<FriendViewModel>()
    private val binding by viewBinding(DialogFriendBinding::bind)
    private val friend by lazy { requireArguments().getParcelable<Friend>(ARG_FRIEND)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<FriendViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()
        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            textLastSeen.text = friend.getLastSeenText(requireContext())
            imageUser.load(friend.image, R.drawable.user_placeholder)
            textName.text = friend.name
            if (friend.status.isEmpty()) {
                textStatus.gone()
            } else {
                textStatus.text = friend.status
            }
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.deleteSuccess.observe(viewLifecycleOwner) {
            dismiss()
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun setupClickListeners() {
        with(binding) {
            optionRemove.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.friend_remove_dialog_title)
                    .setMessage(getString(R.string.friend_remove_dialog_message, friend.name))
                    .setNegativeButton(R.string.dialog_no, null)
                    .setPositiveButton(R.string.dialog_yes) { _, _ ->
                        viewModel.deleteFriend(friend)
                    }
                    .show()
            }
            optionMessage.setOnClickListener {
                (targetFragment as OnMessageClickListener).onMessageClick(friend)
                dismiss()
            }
            optionCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    interface OnMessageClickListener {

        fun onMessageClick(friend: Friend)
    }
}