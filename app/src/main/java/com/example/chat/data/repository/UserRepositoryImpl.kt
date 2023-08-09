package com.example.chat.data.repository

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onSuccess
import com.example.chat.data.local.ChatDatabase
import com.example.chat.data.local.UserStorage
import com.example.chat.data.local.model.UserEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.data.local.model.toEntity
import com.example.chat.data.remote.common.ApiParamBuilder
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.model.dto.toDomain
import com.example.chat.data.remote.service.UserService
import com.example.chat.domain.user.User
import com.example.chat.domain.user.UserRepository
import toothpick.InjectConstructor

@InjectConstructor
class UserRepositoryImpl(
    private val request: Request,
    private val userService: UserService,
    private val userStorage: UserStorage,
    private val chatDatabase: ChatDatabase
) : UserRepository {

    override fun login(email: String, password: String): Either<Failure, User> {
        return userStorage
            .getToken()
            .flatMap { token ->
                val params = ApiParamBuilder()
                    .password(password)
                    .email(email)
                    .token(token)
                    .build()
                request.make(userService.login(params))
            }
            .map { response ->
                response.user.toDomain()
            }
            .onSuccess { user ->
                userStorage.saveUser(user.copy(password = password).toEntity())
            }
    }

    override fun logout(): Either<Failure, None> {
        chatDatabase.clearAllTables()
        return userStorage.clear()
    }

    override fun register(email: String, name: String, password: String): Either<Failure, User> {
        return userStorage
            .getToken()
            .flatMap { token ->
                val params = ApiParamBuilder()
                    .userDate(System.currentTimeMillis())
                    .password(password)
                    .email(email)
                    .token(token)
                    .name(name)
                    .build()
                request.make(userService.register(params))
            }
            .map { response ->
                response.user.toDomain()
            }
            .onSuccess { user ->
                userStorage.saveUser(user.copy(password = password).toEntity())
            }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        val params = ApiParamBuilder()
            .email(email)
            .build()
        return request
            .make(userService.forgetPassword(params))
            .map { None() }
    }

    override fun getUser(): Either<Failure, User> {
        return userStorage
            .getUser()
            .map(UserEntity::toDomain)
    }

    override fun updateToken(token: String): Either<Failure, None> {
        userStorage.saveToken(token)
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .oldToken(user.token)
                    .userId(user.id)
                    .token(token)
                    .build()
                request.make(userService.updateToken(params))
            }
            .map { None() }
    }

    override fun updateUserLastSeen(): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .lastSeen(System.currentTimeMillis())
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(userService.updateUserLastSeen(params))
            }
            .map { None() }
    }

    override fun editUser(user: User): Either<Failure, User> {
        return userStorage
            .getUser()
            .flatMap {
                val params = ApiParamBuilder()
                    .password(user.password)
                    .status(user.status)
                    .email(user.email)
                    .image(user.image)
                    .userId(user.id)
                    .token(it.token)
                    .name(user.name)
                    .build()
                request.make(userService.editUser(params))
            }
            .map { response ->
                response.user.toDomain()
            }
            .onSuccess {
                userStorage.saveUser(user.copy(image = it.image).toEntity())
            }
    }
}