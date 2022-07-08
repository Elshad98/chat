package com.example.chat.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.account.AccountEntity
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.login_btn_login
import kotlinx.android.synthetic.main.fragment_login.login_btn_register
import kotlinx.android.synthetic.main.fragment_login.login_input_email
import kotlinx.android.synthetic.main.fragment_login.login_input_password

class LoginFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_login
    override val titleToolbar = R.string.screen_login

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        accountViewModel = viewModel(AccountViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel.accountData.observe(viewLifecycleOwner, Observer(::renderAccount))
        accountViewModel.failureData.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )
        login_btn_login.setOnClickListener { validateFields() }
        login_btn_register.setOnClickListener {
            activity?.let(navigator::showSignUp)
        }
    }

    private fun validateFields() {
        hideSoftKeyboard()
        val allFields = arrayOf(login_input_email, login_input_password)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        if (allValid) {
            login(login_input_email.text.toString(), login_input_password.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        showProgress()
        accountViewModel.login(email, password)
    }

    private fun renderAccount(accountEntity: AccountEntity?) {
        hideProgress()
        activity?.let {
            navigator.showHome(it)
            it.finish()
        }
    }
}