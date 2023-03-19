package com.example.chat.presentation.settings.email

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
import com.example.chat.databinding.FragmentChangeEmailBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class ChangeEmailFragment : Fragment(R.layout.fragment_change_email) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by viewBinding(FragmentChangeEmailBinding::bind)
    private val viewModel by viewModels<ChangeEmailViewModel>(factoryProducer = { viewModelFactory })

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

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.inputEmailError.observe(viewLifecycleOwner) {
            with(binding) {
                inputLayoutEmail.isErrorEnabled = it
                inputLayoutEmail.error = if (it) {
                    getString(R.string.error_invalid_email)
                } else {
                    null
                }
            }
        }
        viewModel.email.observe(viewLifecycleOwner) { email ->
            binding.textTitle.text = getString(R.string.change_email_header, email)
        }
        viewModel.updateSuccess.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            is Failure.EmailAlreadyExistError -> R.string.error_email_already_exist
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun addTextChangeListener() {
        binding.inputEmail.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputEmail()
        }
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
                        viewModel.changeEmail(inputEmail.trimmedText)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}