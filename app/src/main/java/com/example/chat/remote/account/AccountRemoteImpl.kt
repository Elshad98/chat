package com.example.chat.remote.account

import com.example.chat.data.account.AccountRemote
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.exception.Failure
import com.example.chat.remote.core.Request
import com.example.chat.remote.service.ApiService
import javax.inject.Inject

class AccountRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: ApiService
) : AccountRemote {

    override fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None> {
        val registerMap = createRegisterMap(email, name, password, token, userDate)
        return request.make(service.register(registerMap)) { None() }
    }

    private fun createRegisterMap(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_EMAIL] = email
        map[ApiService.PARAM_NAME] = name
        map[ApiService.PARAM_PASSWORD] = password
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_DATE] = userDate.toString()
        return map
    }
}