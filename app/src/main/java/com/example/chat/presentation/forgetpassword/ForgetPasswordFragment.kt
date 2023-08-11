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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.NavigateUp -> findNavController().navigateUp()
                is State.ValidationError -> showValidationError()
                is State.Error -> handleFailure(state.failure)
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
        with(binding) {
            inputEmail.doOnTextChanged { _, _, _, _ ->
                inputLayoutEmail.isErrorEnabled = false
            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun showValidationError() {
        with(binding) {
            inputLayoutEmail.isErrorEnabled = true
            inputLayoutEmail.error = getString(R.string.error_invalid_email)
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