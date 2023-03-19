package com.example.chat.presentation.settings.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import javax.inject.Inject

class UpdateStatusViewModel @Inject constructor(
    private val getUser: GetUser,
    private val editUser: EditUser
) : BaseViewModel() {

    companion object {

        private const val STATUS_MAX_LENGTH = 70
    }

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _updateSuccess = MutableLiveData<Unit>()
    val updateSuccess: LiveData<Unit>
        get() = _updateSuccess

    init {
        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                _status.value = user.status
            }
        }
    }

    fun updateStatus(status: String) {
        if (status.length > STATUS_MAX_LENGTH) {
            return
        }

        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                editUser(user.copy(status = status)) { either ->
                    either.fold(::handleFailure) {
                        _updateSuccess.value = Unit
                    }
                }
            }
        }
    }
}