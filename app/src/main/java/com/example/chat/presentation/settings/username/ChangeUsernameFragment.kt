package com.example.chat.presentation.settings.username

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
import com.example.chat.databinding.FragmentChangeUsernameBinding
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class ChangeUsernameFragment : Fragment(R.layout.fragment_change_username) {

    private val viewModel by inject<ChangeUsernameViewModel>()
    private val binding by viewBinding(FragmentChangeUsernameBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<ChangeUsernameViewModel>(this)
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
        addTextChangeListener()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.changeUsername(binding.inputUsername.trimmedText)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addTextChangeListener() {
        binding.inputUsername.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputUsername()
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.username.observe(viewLifecycleOwner, binding.inputUsername::setText)
        viewModel.errorInputUsername.observe(viewLifecycleOwner) {
            with(binding) {
                inputLayoutUsername.isErrorEnabled = it
                inputLayoutUsername.error = if (it) {
                    getString(R.string.error_invalid_username)
                } else {
                    null
                }
            }
        }
        viewModel.updateSuccess.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            else -> R.string.error_server
        }.let(::showToast)
    }
}