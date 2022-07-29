package com.example.chat.presentation

import com.example.chat.domain.account.CheckAuth
import com.example.chat.domain.type.None
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(
    private val checkAuth: CheckAuth
) {

    fun userLoggedIn(body: (Boolean) -> Unit) {
        checkAuth(None()) { either ->
            either.fold({ body(false) }, body)
        }
    }
}