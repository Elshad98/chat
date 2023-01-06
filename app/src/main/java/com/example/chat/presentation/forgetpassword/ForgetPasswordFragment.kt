package com.example.chat.presentation.forgetpassword

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
import com.example.chat.databinding.FragmentForgetPasswordBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by viewBinding(FragmentForgetPasswordBinding::bind)
    private val viewModel by viewModels<ForgetPasswordViewModel>(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupToolbar()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListener()
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.resetSuccess.observe(viewLifecycleOwner) { findNavController().navigateUp() }
        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            with(binding) {
                inputLayoutEmail.isErrorEnabled = it
                inputLayoutEmail.error = if (it) {
                    getString(R.string.error_field_required)
                } else {
                    null
                }
            }
        }
    }

    private fun setupClickListener() {
        with(binding) {
            buttonSend.setOnClickListener {
                viewModel.forgetPassword(inputEmail.trimmedText)
            }
        }
    }

    private fun addTextChangeListener() {
        binding.inputEmail.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputEmail()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.ServerError -> showToast(R.string.error_server)
            is Failure.NetworkConnectionError -> showToast(R.string.error_network)
            is Failure.CantSendEmailError -> showToast(getString(R.string.error_cannot_send_email))
            is Failure.EmailNotRegisteredError -> showToast(getString(R.string.email_not_registered))
        }
    }
}