package com.example.chat.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentLoginBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.loginSuccess.observe(viewLifecycleOwner) { launchHomeFragment() }
        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            binding.inputLayoutEmail.isErrorEnabled = it
            binding.inputLayoutEmail.error = if (it) {
                getString(R.string.error_field_required)
            } else {
                null
            }
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            binding.inputLayoutPassword.isErrorEnabled = it
            binding.inputLayoutPassword.error = if (it) {
                getString(R.string.error_field_required)
            } else {
                null
            }
        }
    }

    private fun launchHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun launchRegisterFragment() = Unit

    private fun launchForgetPasswordFragment() = Unit

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
            is Failure.AuthError -> showToast(R.string.error_auth)
            is Failure.ServerError -> showToast(R.string.error_server)
            is Failure.NetworkConnectionError -> showToast(R.string.error_network)
            else -> showToast(R.string.error_something_went_wrong)
        }
    }
}