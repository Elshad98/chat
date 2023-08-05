package com.example.chat.presentation.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.ForgetPassword
import toothpick.InjectConstructor

@InjectConstructor
class ForgetPasswordViewModel(
    private val forgetPassword: ForgetPassword
) : BaseViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>()
    private val _resetSuccess = MutableLiveData<Unit>()
    val errorInputEmail: LiveData<Boolean> = _errorInputEmail
    val resetSuccess: LiveData<Unit> = _resetSuccess

    fun forgetPassword(email: String) {
        if (email.isValidEmail()) {
            forgetPassword(ForgetPassword.Params(email), viewModelScope) { either ->
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