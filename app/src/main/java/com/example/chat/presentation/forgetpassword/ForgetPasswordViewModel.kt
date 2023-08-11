package com.example.chat.presentation.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.ForgetPassword
import toothpick.InjectConstructor

@InjectConstructor
class ForgetPasswordViewModel(
    private val forgetPassword: ForgetPassword
) : BaseViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun forgetPassword(email: String) {
        if (email.isValidEmail()) {
            forgetPassword(ForgetPassword.Params(email), viewModelScope) { either ->
                either.fold(
                    { failure -> _state.value = State.Error(failure) },
                    { _state.value = State.NavigateUp }
                )
            }
        } else {
            _state.value = State.ValidationError
        }
    }
}

sealed class State {

    object NavigateUp : State()
    object ValidationError : State()
    class Error(val failure: Failure) : State()
}