package com.example.chat.presentation.settings.password

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentChangePasswordBinding
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by viewBinding(FragmentChangePasswordBinding::bind)
    private val viewModel: ChangePasswordViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        addTextChangeListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_done, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.done) {
            with(binding) {
                viewModel.changePassword(
                    inputPassword.trimmedText,
                    inputNewPassword.trimmedText,
                    inputConfirmPassword.trimmedText
                )
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> handleFailure(state.failure)
                is State.RedirectToSettings -> findNavController().popBackStack()
                is State.ValidateFields -> showValidationErrors(state.invalidFields)
            }
        }
    }

    private fun showValidationErrors(invalidFields: List<ValidatedFiled>) {
        invalidFields.forEach { validatedField ->
            when (validatedField) {
                ValidatedFiled.PASSWORD ->
                    binding.inputLayoutPassword.error = getString(R.string.error_wrong_password)
                ValidatedFiled.NEW_PASSWORD ->
                    binding.inputLayoutNewPassword.error = getString(R.string.error_invalid_password)
                ValidatedFiled.CONFIRM_PASSWORD ->
                    binding.inputLayoutConfirmPassword.error = getString(R.string.error_password_mismatch)
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun addTextChangeListeners() {
        with(binding) {
            inputPassword.doOnTextChanged { _, _, _, _ ->
                inputLayoutPassword.isErrorEnabled = false
            }
            inputNewPassword.doOnTextChanged { _, _, _, _ ->
                inputLayoutNewPassword.isErrorEnabled = false
            }
            inputConfirmPassword.doOnTextChanged { _, _, _, _ ->
                inputLayoutConfirmPassword.isErrorEnabled = false
            }
        }
    }
}