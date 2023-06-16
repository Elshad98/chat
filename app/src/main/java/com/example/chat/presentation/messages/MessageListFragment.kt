package com.example.chat.presentation.messages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentMessageListBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class MessageListFragment : Fragment(R.layout.fragment_message_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
        showToast("Contact id: ${args.contactId}")
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            title = args.contactName
            setDisplayShowTitleEnabled(true)
        }
    }
}