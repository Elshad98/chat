package com.example.chat.presentation.invitefriend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.extension.isValidEmail
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.AddFriend
import javax.inject.Inject

class InviteFriendViewModel @Inject constructor(
    private val addFriend: AddFriend
) : BaseViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _navigateToHome = MutableLiveData<Unit>()
    val navigateToHome: LiveData<Unit>
        get() = _navigateToHome

    override fun onCleared() {
        addFriend.unsubscribe()
    }

    fun addFriend(email: String) {
        if (email.isValidEmail()) {
            addFriend(AddFriend.Params(email)) { either ->
                either.fold(::handleFailure) { _navigateToHome.value = Unit }
            }
        } else {
            _errorInputEmail.value = true
        }
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }
}