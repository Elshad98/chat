package com.example.chat.presentation.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.core.None
import com.example.chat.core.extension.showToast
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseFragment
import com.example.chat.presentation.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_forget_password.forget_password_btn_send_password
import kotlinx.android.synthetic.main.fragment_forget_password.forget_password_input_email

class ForgetPasswordFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_forget_password
    override val titleToolbar: Int = R.string.screen_forget_password

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        accountViewModel = viewModel(AccountViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel.forgetPasswordData.observe(viewLifecycleOwner, Observer(::onPasswordSent))
        accountViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        forget_password_btn_send_password.setOnClickListener {
            sendPassword()
        }
    }

    private fun sendPassword() {
        if (forget_password_input_email.testValidity()) {
            val email = forget_password_input_email.text.toString()
            accountViewModel.forgetPassword(email)
        }
    }

    private fun onPasswordSent(none: None?) {
        showToast(R.string.email_sent)
        requireActivity().finish()
    }
}