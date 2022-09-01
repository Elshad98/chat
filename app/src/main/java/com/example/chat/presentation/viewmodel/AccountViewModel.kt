package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.account.EditAccount
import com.example.chat.domain.account.ForgetPassword
import com.example.chat.domain.account.GetAccount
import com.example.chat.domain.account.Login
import com.example.chat.domain.account.Logout
import com.example.chat.domain.account.Register
import com.example.chat.domain.account.UpdateLastSeen
import com.example.chat.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val loginUseCase: Login,
    private val logoutUseCase: Logout,
    private val registerUseCase: Register,
    private val getAccountUseCase: GetAccount,
    private val editAccountUseCase: EditAccount,
    private val updateLastSeenUseCase: UpdateLastSeen,
    private val forgetPasswordUseCase: ForgetPassword
) : BaseViewModel() {

    val logoutData: MutableLiveData<None> = MutableLiveData()
    val registerData: MutableLiveData<None> = MutableLiveData()
    val forgetPasswordData: MutableLiveData<None> = MutableLiveData()
    val accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    val editAccountData: MutableLiveData<AccountEntity> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        registerUseCase(Register.Params(email, name, password)) { either ->
            either.fold(::handleFailure, ::handleRegister)
        }
    }

    fun login(email: String, password: String) {
        loginUseCase(Login.Params(email, password)) { either ->
            either.fold(::handleFailure, ::handleAccount)
        }
    }

    fun forgetPassword(email: String) {
        forgetPasswordUseCase(ForgetPassword.Params(email)) { either ->
            either.fold(::handleFailure, ::handleForgetPassword)
        }
    }

    fun getAccount() {
        getAccountUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleAccount)
        }
    }

    fun logout() {
        logoutUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleLogout)
        }
    }

    fun editAccount(account: AccountEntity) {
        editAccountUseCase(account) { either ->
            either.fold(::handleFailure, ::handleEditAccount)
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
        getAccountUseCase.unsubscribe()
        updateLastSeenUseCase.unsubscribe()
        forgetPasswordUseCase.unsubscribe()
    }

    private fun handleForgetPassword(none: None) {
        forgetPasswordData.value = none
    }

    private fun handleRegister(none: None) {
        registerData.value = none
    }

    private fun handleAccount(account: AccountEntity) {
        accountData.value = account
    }

    private fun handleLogout(none: None) {
        logoutData.value = none
    }

    private fun handleEditAccount(account: AccountEntity) {
        editAccountData.value = account
    }
}