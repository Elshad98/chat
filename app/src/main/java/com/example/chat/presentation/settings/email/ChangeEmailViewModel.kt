package com.example.chat.presentation.settings.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import javax.inject.Inject

class ChangeEmailViewModel @Inject constructor(
    private val getUser: GetUser,
    private val editUser: EditUser
) : BaseViewModel() {

    private val _inputEmailError = MutableLiveData<Boolean>()
    val inputEmailError: LiveData<Boolean>
        get() = _inputEmailError

    private val _updateSuccess = MutableLiveData<Unit>()
    val updateSuccess: LiveData<Unit>
        get() = _updateSuccess

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    init {
        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                _email.value = user.email
            }
        }
    }

    override fun onCleared() {
        getUser.unsubscribe()
        editUser.unsubscribe()
    }

    fun changeEmail(email: String) {
        if (!email.isValidEmail()) {
            _inputEmailError.value = true
            return
        }

        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                editUser(user.copy(email = email)) { either ->
                    either.fold(::handleFailure) {
                        _updateSuccess.value = Unit
                    }
                }
            }
        }
    }

    fun resetErrorInputEmail() {
        _inputEmailError.value = false
    }
}