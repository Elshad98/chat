package com.example.chat.presentation.splash

import com.example.chat.core.None
import com.example.chat.domain.user.CheckAuth
import javax.inject.Inject

class Authenticator @Inject constructor(
    private val checkAuth: CheckAuth
) {

    fun userLoggedIn(block: (Boolean) -> Unit) {
        checkAuth(None()) { either ->
            either.fold({ block(false) }, block)
        }
    }
}