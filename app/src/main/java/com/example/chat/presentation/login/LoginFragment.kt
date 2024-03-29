package com.example.chat.presentation.login

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
import com.example.chat.databinding.FragmentLoginBinding
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by inject<LoginViewModel>()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<LoginViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.hide()
        setupClickListeners()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListeners()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.RedirectToHome -> launchHomeFragment()
                is State.Error -> handleFailure(state.failure)
                is State.ValidationError -> showValidationErrors(state.invalidFields)
            }
        }
    }

    private fun launchHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun launchRegisterFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun launchForgetPasswordFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }

    private fun setupClickListeners() {
        with(binding) {
            textForgetPassword.setOnClickListener {
                launchForgetPasswordFragment()
            }
            textRegister.setOnClickListener {
                launchRegisterFragment()
            }
            buttonLogin.setOnClickListener {
                viewModel.login(inputEmail.trimmedText, inputPassword.trimmedText)
            }
        }
    }

    private fun addTextChangeListeners() {
        with(binding) {
            inputEmail.doOnTextChanged { _, _, _, _ ->
                inputLayoutEmail.isErrorEnabled = false
            }
            inputPassword.doOnTextChanged { _, _, _, _ ->
                inputLayoutPassword.isErrorEnabled = false
            }
        }
    }

    private fun showValidationErrors(invalidFields: List<ValidatedField>) {
        invalidFields.forEach { validatedField ->
            when (validatedField) {
                ValidatedField.EMAIL ->
                    binding.inputLayoutEmail.error = getString(R.string.error_field_required)
                ValidatedField.PASSWORD ->
                    binding.inputLayoutPassword.error = getString(R.string.error_field_required)
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.AuthError -> R.string.error_auth
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }
}