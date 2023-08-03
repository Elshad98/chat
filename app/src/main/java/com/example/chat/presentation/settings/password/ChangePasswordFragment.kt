package com.example.chat.presentation.settings.password

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentChangePasswordBinding
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {

    private val viewModel by inject<ChangePasswordViewModel>()
    private val binding by viewBinding(FragmentChangePasswordBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<ChangePasswordViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
        setHasOptionsMenu(true)
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
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                with(binding) {
                    viewModel.changePassword(
                        inputPassword.trimmedText,
                        inputNewPassword.trimmedText,
                        inputConfirmPassword.trimmedText
                    )
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
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