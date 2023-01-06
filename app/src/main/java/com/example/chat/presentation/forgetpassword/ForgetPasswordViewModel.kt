package com.example.chat.presentation.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.ForgetPassword
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
    private val forgetPassword: ForgetPassword
) : BaseViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _resetSuccess = MutableLiveData<Unit>()
    val resetSuccess: LiveData<Unit>
        get() = _resetSuccess

    fun forgetPassword(email: String) {
        if (email.isEmpty()) {
            _errorInputEmail.value = true
        } else {
            forgetPassword(ForgetPassword.Params(email)) { either ->
                either.fold(::handleFailure) { _resetSuccess.value = Unit }
            }
        }
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }
}