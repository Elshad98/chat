package com.example.chat.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.Login
import toothpick.InjectConstructor

@InjectConstructor
class LoginViewModel(
    private val login: Login
) : BaseViewModel() {

    private val _loginSuccess = MutableLiveData<Unit>()
    private val _errorInputEmail = MutableLiveData<Boolean>()
    private val _errorInputPassword = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Unit> = _loginSuccess
    val errorInputEmail: LiveData<Boolean> = _errorInputEmail
    val errorInputPassword: LiveData<Boolean> = _errorInputPassword
    fun login(email: String, password: String) {
        if (validateInput(email, password)) {
            login(Login.Params(email, password), viewModelScope) { either ->
                either.fold(::handleFailure) { _loginSuccess.value = Unit }
            }
        }
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    private fun validateInput(email: String, password: String): Boolean {
        var result = true
        if (email.isEmpty()) {
            _errorInputEmail.value = true
            result = false
        }
        if (password.isEmpty()) {
            _errorInputPassword.value = true
            result = false
        }
        return result
    }
}