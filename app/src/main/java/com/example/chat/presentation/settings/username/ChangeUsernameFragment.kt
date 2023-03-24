package com.example.chat.presentation.settings.username

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentChangeUsernameBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class ChangeUsernameFragment : Fragment(R.layout.fragment_change_username) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by viewBinding(FragmentChangeUsernameBinding::bind)
    private val viewModel: ChangeUsernameViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListener()
    }

    private fun addTextChangeListener() {
        binding.inputUsername.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputUsername()
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.username.observe(viewLifecycleOwner, binding.inputUsername::setText)
        viewModel.errorInputUsername.observe(viewLifecycleOwner) {
            with(binding) {
                inputLayoutUsername.isErrorEnabled = it
                inputLayoutUsername.error = if (it) {
                    getString(R.string.error_invalid_username)
                } else {
                    null
                }
            }
        }
        viewModel.updateSuccess.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun setupToolbar() {
        supportActionBar?.hide()
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.done -> {
                        viewModel.changeUsername(inputUsername.trimmedText)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}