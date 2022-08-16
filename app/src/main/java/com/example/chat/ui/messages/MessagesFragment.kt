package com.example.chat.ui.messages

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.cache.ChatDatabase
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.extensions.longToast
import com.example.chat.presentation.viewmodel.MessagesViewModel
import com.example.chat.remote.service.ApiService
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_messages.messages_btn_send
import kotlinx.android.synthetic.main.fragment_messages.messages_input_text

class MessagesFragment : BaseFragment() {

    override val titleToolbar = R.string.chats
    override val layoutId = R.layout.fragment_messages

    lateinit var messagesViewModel: MessagesViewModel

    private lateinit var recyclerView: RecyclerView

    private val viewAdapter = MessagesAdapter()
    private var contactName = ""
    private var contactId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            adapter = viewAdapter
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
        }

        messagesViewModel.getMessagesData.observe(viewLifecycleOwner, Observer(::handleMessages))
        messagesViewModel.progressData.observe(viewLifecycleOwner, Observer(::updateProgress))
        messagesViewModel.failureData.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

        base {
            val args = intent.getBundleExtra("args")
            if (args == null) {
                contactId = intent.getLongExtra(ApiService.PARAM_CONTACT_ID, 0L)
                contactName = intent.getStringExtra(ApiService.PARAM_NAME) ?: ""
            } else {
                contactId = args.getLong(ApiService.PARAM_CONTACT_ID)
                contactName = args.getString(ApiService.PARAM_NAME, "")
            }
        }

        messages_btn_send.setOnClickListener {
            sendMessage()
        }

        ChatDatabase.getInstance(requireContext())
            .messagesDao
            .getLiveMessagesWithContact(contactId)
            .observe(viewLifecycleOwner, Observer(::handleMessages))
    }

    override fun onResume() {
        super.onResume()
        base {
            supportActionBar?.title = contactName
        }

        messagesViewModel.getMessages(contactId)
    }

    private fun handleMessages(messages: List<MessageEntity>?) {
        if (messages != null) {
            viewAdapter.submitList(messages)
            Handler().postDelayed(
                { recyclerView.smoothScrollToPosition(viewAdapter.itemCount - 1) },
                100
            )
        }
    }

    private fun sendMessage() {
        if (contactId == 0L) {
            return
        }

        val text = messages_input_text.text.toString()

        if (text.isBlank()) {
            requireContext().longToast("Введите текст")
            return
        }

        showProgress()
        messagesViewModel.sendMessage(contactId, text, "")
        messages_input_text.text.clear()
    }
}