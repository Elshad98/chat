package com.example.chat.data.remote

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.remote.common.ApiParamBuilder
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.UserResponse
import com.example.chat.data.remote.service.UserService
import toothpick.InjectConstructor

@InjectConstructor
class UserRemoteDataSource(
    private val request: Request,
    private val service: UserService
) {

    fun forgetPassword(email: String): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .email(email)
            .build()
        return request.make(service.forgetPassword(params))
    }

    fun login(email: String, password: String, token: String): Either<Failure, UserResponse> {
        val params = ApiParamBuilder()
            .password(password)
            .email(email)
            .token(token)
            .build()
        return request.make(service.login(params))
    }

    fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .oldToken(oldToken)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.updateToken(params))
    }

    fun updateUserLastSeen(
        userId: Long,
        token: String,
        lastSeen: Long
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .lastSeen(lastSeen)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.updateUserLastSeen(params))
    }

    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, UserResponse> {
        val params = ApiParamBuilder()
            .password(password)
            .userDate(userDate)
            .email(email)
            .token(token)
            .name(name)
            .build()
        return request.make(service.register(params))
    }

    fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Either<Failure, UserResponse> {
        val params = ApiParamBuilder()
            .password(password)
            .userId(userId)
            .status(status)
            .email(email)
            .token(token)
            .image(image)
            .name(name)
            .build()
        return request.make(service.editUser(params))
    }
}