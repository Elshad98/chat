package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.account.EditAccount
import com.example.chat.domain.account.GetAccount
import com.example.chat.domain.account.Login
import com.example.chat.domain.account.Logout
import com.example.chat.domain.account.Register
import com.example.chat.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    val loginUseCase: Login,
    val logoutUseCase: Logout,
    val registerUseCase: Register,
    val getAccountUseCase: GetAccount,
    val editAccountUseCase: EditAccount
) : BaseViewModel() {

    var registerData: MutableLiveData<None> = MutableLiveData()
    val accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    val logoutData: MutableLiveData<None> = MutableLiveData()
    val editAccountData: MutableLiveData<AccountEntity> = MutableLiveData()

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

    fun editAccount(account: AccountEntity) {
        editAccountUseCase(account) {
            it.either(::handleFailure, ::handleEditAccount)
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

    private fun handleEditAccount(account: AccountEntity) {
        editAccountData.value = account
    }

    override fun onCleared() {
        super.onCleared()
        registerUseCase.unsubscribe()
        loginUseCase.unsubscribe()
        getAccountUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
    }
}