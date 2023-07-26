package com.example.chat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.message.GetChats
import com.example.chat.domain.message.GetLiveChats
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.Logout
import com.example.chat.domain.user.UpdateLastSeen
import com.example.chat.domain.user.User
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    getLiveChats: GetLiveChats,
    private val logout: Logout,
    private val getUser: GetUser,
    private val getChats: GetChats,
    private val updateLastSeen: UpdateLastSeen
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    private val _navigateToLogin = MutableLiveData<Unit>()
    val user: LiveData<User> = _user
    val navigateToLogin: LiveData<Unit> = _navigateToLogin
    val chatList = getLiveChats()

    override fun onCleared() {
        logout.unsubscribe()
        getUser.unsubscribe()
        getChats.unsubscribe()
        updateLastSeen.unsubscribe()
    }

    fun getChats() {
        getChats(GetChats.Params(true)) { either ->
            either.fold(::handleFailure) { }
        }
    }

    fun logout() {
        logout(None()) { either ->
            either.fold(::handleFailure) { _navigateToLogin.value = Unit }
        }
    }

    fun getUser() {
        getUser(None()) { either ->
            either.fold(::handleFailure, _user::setValue)
        }
    }

    fun updateLastSeen() {
        updateLastSeen(None()) { either ->
            either.fold(::handleFailure) {}
        }
    }
}