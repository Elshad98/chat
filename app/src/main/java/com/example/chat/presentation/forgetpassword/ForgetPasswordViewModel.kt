package com.example.chat.presentation.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.ForgetPassword
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
    private val forgetPassword: ForgetPassword
) : BaseViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>()
    private val _resetSuccess = MutableLiveData<Unit>()
    val errorInputEmail: LiveData<Boolean> = _errorInputEmail
    val resetSuccess: LiveData<Unit> = _resetSuccess

    override fun onCleared() {
        forgetPassword.unsubscribe()
    }

    fun forgetPassword(email: String) {
        if (email.isValidEmail()) {
            forgetPassword(ForgetPassword.Params(email)) { either ->
                either.fold(::handleFailure) { _resetSuccess.value = Unit }
            }
        } else {
            _errorInputEmail.value = true
        }
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }
}