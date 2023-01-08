package com.example.chat.presentation.register

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
import com.example.chat.databinding.FragmentRegisterBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class RegisterFragment : Fragment(R.layout.fragment_register) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModels<RegisterViewModel>(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupClickListeners()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            textSignIn.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonRegister.setOnClickListener {
                viewModel.register(
                    inputUsername.trimmedText,
                    inputEmail.trimmedText,
                    inputPassword.trimmedText
                )
            }
        }
    }

    private fun addTextChangeListeners() {
        with(binding) {
            inputUsername.doOnTextChanged { _, _, _, _ ->
                inputLayoutUsername.isErrorEnabled = false
            }
            inputEmail.doOnTextChanged { _, _, _, _ ->
                inputLayoutEmail.isErrorEnabled = false
            }
            inputPassword.doOnTextChanged { _, _, _, _ ->
                inputLayoutPassword.isErrorEnabled = false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> handleFailure(state.failure)
                is State.RedirectToHome -> launchHomeFragment()
                is State.ValidationError -> showValidationErrors(state.invalidFields)
            }
        }
    }

    private fun showValidationErrors(invalidFields: List<ValidatedField>) {
        invalidFields.forEach { validatedField ->
            when (validatedField) {
                ValidatedField.EMAIL ->
                    binding.inputLayoutEmail.error = getString(R.string.error_invalid_email)
                ValidatedField.USERNAME ->
                    binding.inputLayoutUsername.error = getString(R.string.error_invalid_username)
                ValidatedField.PASSWORD ->
                    binding.inputLayoutPassword.error = getString(R.string.error_invalid_password)
            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun launchHomeFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> showToast(R.string.error_network)
            is Failure.EmailAlreadyExistError -> showToast(R.string.error_email_already_exist)
            else -> showToast(R.string.error_server)
        }
    }
}