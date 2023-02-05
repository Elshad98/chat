package com.example.chat.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.Login
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val login: Login
) : BaseViewModel() {

    private val _loginSuccess = MutableLiveData<Unit>()
    val loginSuccess: LiveData<Unit>
        get() = _loginSuccess

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    override fun onCleared() {
        login.unsubscribe()
    }

    fun login(email: String, password: String) {
        if (validateInput(email, password)) {
            login(Login.Params(email, password)) { either ->
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