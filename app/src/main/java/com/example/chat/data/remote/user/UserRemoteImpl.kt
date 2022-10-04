package com.example.chat.data.remote.user

import com.example.chat.data.remote.core.ApiParamBuilder
import com.example.chat.data.remote.core.Request
import com.example.chat.data.remote.service.UserService
import com.example.chat.data.repository.user.UserRemote
import com.example.chat.core.functional.Either
import com.example.chat.core.exception.Failure
import com.example.chat.core.None
import com.example.chat.domain.user.User
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: UserService
) : UserRemote {

    override fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None> {
        val params = ApiParamBuilder()
            .password(password)
            .userDate(userDate)
            .email(email)
            .token(token)
            .name(name)
            .build()
        return request.make(service.register(params)) { None() }
    }

    override fun login(email: String, password: String, token: String): Either<Failure, User> {
        val params = ApiParamBuilder()
            .password(password)
            .email(email)
            .token(token)
            .build()
        return request.make(service.login(params)) { it.user }
    }

    override fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None> {
        val params = ApiParamBuilder()
            .userId(userId)
            .oldToken(oldToken)
            .token(token)
            .build()
        return request.make(service.updateToken(params)) { None() }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        val params = ApiParamBuilder()
            .email(email)
            .build()
        return request.make(service.forgetPassword(params)) { None() }
    }

    override fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Either<Failure, User> {
        val params = createUserEditMap(userId, email, name, password, status, token, image)
        return request.make(service.editUser(params)) { it.user }
    }

    override fun updateUserLastSeen(
        userId: Long,
        token: String,
        lastSeen: Long
    ): Either<Failure, None> {
        val params = ApiParamBuilder()
            .userId(userId)
            .lastSeen(lastSeen)
            .token(token)
            .build()
        return request.make(service.updateUserLastSeen(params)) { None() }
    }

    private fun createUserEditMap(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[UserService.PARAM_NAME] = name
        map[UserService.PARAM_EMAIL] = email
        map[UserService.PARAM_TOKEN] = token
        map[UserService.PARAM_STATUS] = status
        map[UserService.PARAM_PASSWORD] = password
        map[UserService.PARAM_USER_ID] = userId.toString()
        if (image.startsWith("../")) {
            map[UserService.PARAM_IMAGE_UPLOADED] = image
        } else {
            map[UserService.PARAM_IMAGE_NEW] = image
            map[UserService.PARAM_IMAGE_NEW_NAME] =
                "user_${userId}_${System.currentTimeMillis()}_photo"
        }
        return map
    }
}