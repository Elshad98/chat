package com.example.chat.presentation.settings.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.isValidPassword
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.User
import toothpick.InjectConstructor

@InjectConstructor
class ChangePasswordViewModel(
    private val getUser: GetUser,
    private val editUser: EditUser
) : BaseViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun changePassword(password: String, newPassword: String, confirmPassword: String) {
        getUser(None(), viewModelScope) { either ->
            either.fold(
                { failure ->
                    _state.value = State.Error(failure)
                },
                { user ->
                    val invalidFields = getInvalidFields(user.password, password, newPassword, confirmPassword)
                    if (invalidFields.isEmpty()) {
                        changePassword(user.copy(password = newPassword))
                    } else {
                        _state.value = State.ValidateFields(invalidFields)
                    }
                }
            )
        }
    }

    private fun getInvalidFields(
        currentPassword: String,
        password: String,
        newPassword: String,
        confirmPassword: String
    ): List<ValidatedFiled> {
        return ArrayList<ValidatedFiled>().apply {
            if (currentPassword != password) {
                add(ValidatedFiled.PASSWORD)
            }
            if (!newPassword.isValidPassword()) {
                add(ValidatedFiled.NEW_PASSWORD)
            }
            if (newPassword != confirmPassword) {
                add(ValidatedFiled.CONFIRM_PASSWORD)
            }
        }
    }

    private fun changePassword(user: User) {
        editUser(EditUser.Params(user), viewModelScope) { either ->
            either.fold(
                { failure -> _state.value = State.Error(failure) },
                { _state.value = State.RedirectToSettings }
            )
        }
    }
}

sealed class State {

    object RedirectToSettings : State()
    class Error(val failure: Failure) : State()
    class ValidateFields(val invalidFields: List<ValidatedFiled>) : State()
}

enum class ValidatedFiled {
    PASSWORD,
    NEW_PASSWORD,
    CONFIRM_PASSWORD
}