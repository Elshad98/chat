package com.example.chat.data.account

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.Failure

interface AccountRemote {

    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>
}