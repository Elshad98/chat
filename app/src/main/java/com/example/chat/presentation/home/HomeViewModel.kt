package com.example.chat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.User
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getUser: GetUser
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUser() {
        getUser(None()) { either ->
            either.fold(::handleFailure, _user::setValue)
        }
    }
}