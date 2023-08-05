package com.example.chat.presentation.settings.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.None
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import toothpick.InjectConstructor

@InjectConstructor
class ChangeEmailViewModel(
    private val getUser: GetUser,
    private val editUser: EditUser
) : BaseViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>()
    private val _updateSuccess = MutableLiveData<Unit>()
    private val _email = MutableLiveData<String>()
    val errorInputEmail: LiveData<Boolean> = _errorInputEmail
    val updateSuccess: LiveData<Unit> = _updateSuccess
    val email: LiveData<String> = _email

    init {
        getUser(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { user ->
                _email.value = user.email
            }
        }
    }

    fun changeEmail(email: String) {
        if (!email.isValidEmail()) {
            _errorInputEmail.value = true
            return
        }

        getUser(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { user ->
                editUser(EditUser.Params(user.copy(email = email)), viewModelScope) { either ->
                    either.fold(::handleFailure) {
                        _updateSuccess.value = Unit
                    }
                }
            }
        }
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }
}