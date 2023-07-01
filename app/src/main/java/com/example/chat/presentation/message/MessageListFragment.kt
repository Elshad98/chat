package com.example.chat.presentation.message

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.extension.supportActionBar
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentMessageListBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.domain.message.Message
import com.example.chat.presentation.App
import javax.inject.Inject

class MessageListFragment : Fragment(R.layout.fragment_message_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: MessageAdapter
    private val args by navArgs<MessageListFragmentArgs>()
    private val binding by viewBinding(FragmentMessageListBinding::bind)
    private val viewModel: MessageListViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        with(binding) {
            buttonSendMessage.setOnClickListener {
                viewModel.sendMessage(args.contactId, inputMessage.trimmedText, "")
                inputMessage.text?.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter(
            onMessageLongClickListener = { message ->
            }
        )
        binding.recyclerView.apply {
            adapter = this@MessageListFragment.adapter
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            title = args.contactName
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun observeViewModel() {
        viewModel.getMessages(args.contactId).observe(viewLifecycleOwner, ::handleMessageList)
    }

    private fun handleMessageList(messages: List<Message>) {
        adapter.submitList(messages)
    }
}