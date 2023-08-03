package com.example.chat.presentation.forgetpassword

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
import com.example.chat.databinding.FragmentForgetPasswordBinding
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {

    private val viewModel by inject<ForgetPasswordViewModel>()
    private val binding by viewBinding(FragmentForgetPasswordBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<ForgetPasswordViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
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
                    getString(R.string.error_invalid_email)
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
            is Failure.NetworkConnectionError -> R.string.error_network
            is Failure.CantSendEmailError -> R.string.error_cannot_send_email
            is Failure.EmailNotRegisteredError -> R.string.email_not_registered
            else -> R.string.error_server
        }.let(::showToast)
    }
}