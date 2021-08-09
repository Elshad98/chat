package com.example.chat.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.App
import com.example.chat.R
import com.example.chat.domain.type.None
import com.example.chat.extensions.longToast
import com.example.chat.presentation.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_register
    override val titleToolbar = R.string.register

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel(AccountViewModel::class.java)
        accountViewModel.registerData.observe(this, Observer(::handleRegister))
        accountViewModel.failureData.observe(
            this,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_btn_new_membership.setOnClickListener {
            register()
        }
    }

    private fun validateFields(): Boolean {
        val allFields = arrayOf(
            register_input_email,
            register_input_password,
            register_input_confirm_password,
            register_input_username
        )
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid && validatePasswords()
    }

    private fun validatePasswords(): Boolean {
        val valid = register_input_password.text.toString() == register_input_confirm_password.text.toString()
        if (!valid) {
            requireContext().longToast(R.string.error_password_mismatch)
        }
        return valid
    }

    private fun register() {
        hideSoftKeyboard()

        val allValid = validateFields()

        if (allValid) {
            showProgress()

            accountViewModel.register(
                register_input_email.text.toString(),
                register_input_username.text.toString(),
                register_input_password.text.toString()
            )
        }
    }

    private fun handleRegister(none: None? = None()) {
        hideProgress()
        context?.longToast(R.string.account_created)
    }
}