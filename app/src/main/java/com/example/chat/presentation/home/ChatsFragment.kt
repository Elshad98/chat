package com.example.chat.presentation.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.data.local.AppDatabase
import com.example.chat.data.local.model.MessageEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.domain.message.Message
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseListFragment
import com.example.chat.presentation.viewmodel.MessagesViewModel

class ChatsFragment : BaseListFragment() {

    override val viewAdapter = ChatsAdapter()
    override val titleToolbar = R.string.chats

    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        messagesViewModel = viewModel(MessagesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesViewModel.progressData.observe(viewLifecycleOwner, Observer(::updateProgress))
        messagesViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        viewAdapter.setOnClick(
            { message, _ ->
                (message as? Message)?.let {
                    val contact = it.contact
                    navigator.showChatWithContact(requireActivity(), contact.id, contact.name)
                }
            }
        )

        AppDatabase.getInstance(requireContext()).messageDao().getLiveChats().observe(
            viewLifecycleOwner,
            Observer { messages ->
                val list = messages.distinctBy { it.contact.id }.toList()
                handleChats(list.map(MessageEntity::toDomain))
            }
        )
    }

    override fun onResume() {
        super.onResume()
        messagesViewModel.getChats()
    }

    private fun handleChats(messages: List<Message>?) {
        if (messages != null && messages.isNotEmpty()) {
            viewAdapter.submitList(messages)
        }
    }
}