package com.example.chat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.message.GetChats
import com.example.chat.domain.message.GetLiveChats
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.Logout
import com.example.chat.domain.user.UpdateLastSeen
import com.example.chat.domain.user.User
import toothpick.InjectConstructor

@InjectConstructor
class HomeViewModel(
    getLiveChats: GetLiveChats,
    private val logout: Logout,
    private val getUser: GetUser,
    private val getChats: GetChats,
    private val updateLastSeen: UpdateLastSeen
) : BaseViewModel() {

    private val _navigateToLogin = MutableLiveData<Unit>()
    private val _user = MutableLiveData<User>()
    val navigateToLogin: LiveData<Unit> = _navigateToLogin
    val user: LiveData<User> = _user
    val chatList = getLiveChats()

    fun logout() {
        logout(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { _navigateToLogin.value = Unit }
        }
    }

    fun getUser() {
        getUser(None(), viewModelScope) { either ->
            either.fold(::handleFailure, _user::setValue)
        }
    }

    fun getChats() {
        getChats(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { }
        }
    }

    fun updateLastSeen() {
        updateLastSeen(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { }
        }
    }
}