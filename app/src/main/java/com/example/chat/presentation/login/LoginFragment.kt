package com.example.chat.presentation.login

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
import com.example.chat.databinding.FragmentLoginBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
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
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.loginSuccess.observe(viewLifecycleOwner) { launchHomeFragment() }
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
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            with(binding) {
                inputLayoutPassword.isErrorEnabled = it
                inputLayoutPassword.error = if (it) {
                    getString(R.string.error_field_required)
                } else {
                    null
                }
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
                viewModel.resetErrorInputEmail()
            }
            inputPassword.doOnTextChanged { _, _, _, _ ->
                viewModel.resetErrorInputPassword()
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