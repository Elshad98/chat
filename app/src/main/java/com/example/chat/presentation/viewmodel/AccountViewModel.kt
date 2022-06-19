package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.account.GetAccount
import com.example.chat.domain.account.Login
import com.example.chat.domain.account.Logout
import com.example.chat.domain.account.Register
import com.example.chat.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    val registerUseCase: Register,
    val loginUseCase: Login,
    val getAccountUseCase: GetAccount,
    val logoutUseCase: Logout
) : BaseViewModel() {

    var registerData: MutableLiveData<None> = MutableLiveData()
    val accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    val logoutData: MutableLiveData<None> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        registerUseCase(Register.Params(email, name, password)) {
            it.either(::handleFailure, ::handleRegister)
        }
    }

    fun login(email: String, password: String) {
        loginUseCase(Login.Params(email, password)) {
            it.either(::handleFailure, ::handleAccount)
        }
    }

    fun getAccount() {
        getAccountUseCase(None()) {
            it.either(::handleFailure, ::handleAccount)
        }
    }

    fun logout() {
        logoutUseCase(None()) {
            it.either(::handleFailure, ::handleLogout)
        }
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

    override fun onCleared() {
        super.onCleared()
        registerUseCase.unsubscribe()
        loginUseCase.unsubscribe()
        getAccountUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
    }
}