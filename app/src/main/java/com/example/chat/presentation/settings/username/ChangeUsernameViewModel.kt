package com.example.chat.presentation.settings.username

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.extension.isValidUsername
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import javax.inject.Inject

class ChangeUsernameViewModel @Inject constructor(
    private val getUser: GetUser,
    private val editUser: EditUser
) : BaseViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _updateSuccess = MutableLiveData<Unit>()
    val updateSuccess: LiveData<Unit>
        get() = _updateSuccess

    private val _errorInputUsername = MutableLiveData<Boolean>()
    val errorInputUsername: LiveData<Boolean>
        get() = _errorInputUsername

    init {
        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                _username.value = user.name
            }
        }
    }

    override fun onCleared() {
        getUser.unsubscribe()
        editUser.unsubscribe()
    }

    fun changeUsername(username: String) {
        if (!username.isValidUsername()) {
            _errorInputUsername.value = true
            return
        }

        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                editUser(user.copy(name = username)) { either ->
                    either.fold(::handleFailure) {
                        _updateSuccess.value = Unit
                    }
                }
            }
        }
    }

    fun resetErrorInputUsername() {
        _errorInputUsername.value = false
    }
}