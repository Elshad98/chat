package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.type.None
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.ForgetPassword
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.Login
import com.example.chat.domain.user.Logout
import com.example.chat.domain.user.Register
import com.example.chat.domain.user.UpdateLastSeen
import com.example.chat.domain.user.User
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val loginUseCase: Login,
    private val logoutUseCase: Logout,
    private val registerUseCase: Register,
    private val getUserUseCase: GetUser,
    private val editUserUseCase: EditUser,
    private val updateLastSeenUseCase: UpdateLastSeen,
    private val forgetPasswordUseCase: ForgetPassword
) : BaseViewModel() {

    val logoutData: MutableLiveData<None> = MutableLiveData()
    val registerData: MutableLiveData<None> = MutableLiveData()
    val forgetPasswordData: MutableLiveData<None> = MutableLiveData()
    val userData: MutableLiveData<User> = MutableLiveData()
    val editUserData: MutableLiveData<User> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        registerUseCase(Register.Params(email, name, password)) { either ->
            either.fold(::handleFailure, ::handleRegister)
        }
    }

    fun login(email: String, password: String) {
        loginUseCase(Login.Params(email, password)) { either ->
            either.fold(::handleFailure, ::handleUser)
        }
    }

    fun forgetPassword(email: String) {
        forgetPasswordUseCase(ForgetPassword.Params(email)) { either ->
            either.fold(::handleFailure, ::handleForgetPassword)
        }
    }

    fun getUser() {
        getUserUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleUser)
        }
    }

    fun logout() {
        logoutUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleLogout)
        }
    }

    fun editUser(user: User) {
        editUserUseCase(user) { either ->
            either.fold(::handleFailure, ::handleEditUser)
        }
    }

    fun updateLastSeen() {
        updateLastSeenUseCase(None()) { either ->
            either.fold(::handleFailure) {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
        registerUseCase.unsubscribe()
        getUserUseCase.unsubscribe()
        updateLastSeenUseCase.unsubscribe()
        forgetPasswordUseCase.unsubscribe()
    }

    private fun handleForgetPassword(none: None) {
        forgetPasswordData.value = none
    }

    private fun handleRegister(none: None) {
        registerData.value = none
    }

    private fun handleUser(user: User) {
        userData.value = user
    }

    private fun handleLogout(none: None) {
        logoutData.value = none
    }

    private fun handleEditUser(user: User) {
        editUserData.value = user
    }
}