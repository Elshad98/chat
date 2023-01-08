package com.example.chat.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.extension.isValidPassword
import com.example.chat.core.extension.isValidUsername
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.Register
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val register: Register
) : BaseViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun register(username: String, email: String, password: String) {
        val invalidFields = getInvalidField(username, email, password)

        if (invalidFields.isEmpty()) {
            register(Register.Params(email, username, password)) { either ->
                either.fold(
                    { failure -> _state.value = State.Error(failure) },
                    { _state.value = State.RedirectToHome }
                )
            }
        } else {
            _state.value = State.ValidationError(invalidFields)
        }
    }

    private fun getInvalidField(
        username: String,
        email: String,
        password: String
    ): List<ValidatedField> {
        return ArrayList<ValidatedField>().apply {
            if (!username.isValidUsername()) {
                add(ValidatedField.USERNAME)
            }
            if (!email.isValidEmail()) {
                add(ValidatedField.EMAIL)
            }
            if (!password.isValidPassword()) {
                add(ValidatedField.PASSWORD)
            }
        }
    }
}

sealed class State {

    object RedirectToHome : State()
    class Error(val failure: Failure) : State()
    class ValidationError(val invalidFields: List<ValidatedField>) : State()
}

enum class ValidatedField {
    USERNAME,
    EMAIL,
    PASSWORD
}