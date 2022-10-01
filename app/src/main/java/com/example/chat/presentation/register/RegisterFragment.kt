package com.example.chat.presentation.register

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.type.None
import com.example.chat.domain.user.User
import com.example.chat.extensions.hideKeyboard
import com.example.chat.extensions.showToast
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseFragment
import com.example.chat.presentation.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_register.register_btn_already_have_account
import kotlinx.android.synthetic.main.fragment_register.register_btn_new_membership
import kotlinx.android.synthetic.main.fragment_register.register_input_confirm_password
import kotlinx.android.synthetic.main.fragment_register.register_input_email
import kotlinx.android.synthetic.main.fragment_register.register_input_password
import kotlinx.android.synthetic.main.fragment_register.register_input_username

class RegisterFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_register
    override val titleToolbar = R.string.register

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        accountViewModel = viewModel(AccountViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel.registerData.observe(viewLifecycleOwner, Observer(::handleRegister))
        accountViewModel.userData.observe(viewLifecycleOwner, Observer(::handleLogin))
        accountViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        register_btn_new_membership.setOnClickListener {
            register()
        }
        register_btn_already_have_account.setOnClickListener {
            requireActivity().finish()
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
            showToast(R.string.error_password_mismatch)
        }
        return valid
    }

    private fun register() {
        requireActivity().hideKeyboard()

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

    private fun handleLogin(user: User) {
        hideProgress()
        requireActivity().let {
            navigator.showHome(it)
            it.finish()
        }
    }

    private fun handleRegister(none: None? = None()) {
        accountViewModel.login(
            register_input_email.text.toString(),
            register_input_password.text.toString()
        )
    }
}