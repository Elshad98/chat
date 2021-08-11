package com.example.chat.data.account

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.None
import com.example.chat.domain.type.Failure

interface AccountCache {

    fun getToken(): Either<Failure, String>

    fun saveToken(token: String): Either<Failure, None>
}