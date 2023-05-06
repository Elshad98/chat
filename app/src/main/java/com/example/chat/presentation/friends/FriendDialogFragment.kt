package com.example.chat.presentation.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.extension.gone
import com.example.chat.core.extension.load
import com.example.chat.databinding.FragmentFriendBinding
import com.example.chat.domain.friend.Friend
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

    private val friend by lazy { requireArguments().getParcelable<Friend>(ARG_FRIEND)!! }
    private val binding by viewBinding(FragmentFriendBinding::bind)
    private var onFriendClickListener: OnFriendClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()
    }

    fun setOnFriendClickListener(onFriendClickListener: OnFriendClickListener) {
        this.onFriendClickListener = onFriendClickListener
    }

    private fun bindViews() {
        with(binding) {
            imageUser.load(friend.image, R.drawable.user_placeholder)
            textName.text = friend.name
            if (friend.status.isEmpty()) {
                textStatus.gone()
            } else {
                textStatus.text = friend.status
            }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            optionRemove.setOnClickListener {
                onFriendClickListener?.onRemoveClick(friend)
            }
            optionMessage.setOnClickListener {
                onFriendClickListener?.onMessageClick(friend)
            }
            optionCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    interface OnFriendClickListener {

        fun onRemoveClick(friend: Friend)

        fun onMessageClick(friend: Friend)
    }
}