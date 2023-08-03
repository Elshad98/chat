package com.example.chat.data.repository

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onSuccess
import com.example.chat.data.local.UserLocalDataSource
import com.example.chat.data.local.model.UserEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.data.local.model.toEntity
import com.example.chat.data.remote.UserRemoteDataSource
import com.example.chat.data.remote.model.dto.toDomain
import com.example.chat.domain.user.User
import com.example.chat.domain.user.UserRepository
import toothpick.InjectConstructor

@InjectConstructor
class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun login(email: String, password: String): Either<Failure, User> {
        return userLocalDataSource
            .getToken()
            .flatMap { token ->
                userRemoteDataSource
                    .login(email, password, token)
                    .map { response -> response.user.toDomain() }
            }
            .onSuccess { user ->
                userLocalDataSource.saveUser(user.copy(password = password).toEntity())
            }
    }

    override fun logout(): Either<Failure, None> {
        return userLocalDataSource.logout()
    }

    override fun checkAuth(): Either<Failure, Boolean> {
        return userLocalDataSource.checkAuth()
    }

    override fun register(email: String, name: String, password: String): Either<Failure, User> {
        return userLocalDataSource
            .getToken()
            .flatMap { token ->
                userRemoteDataSource
                    .register(email, name, password, token, userDate = System.currentTimeMillis())
                    .map { response -> response.user.toDomain() }
            }
            .onSuccess { user ->
                userLocalDataSource.saveUser(user.copy(password = password).toEntity())
            }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        return userRemoteDataSource
            .forgetPassword(email)
            .map { None() }
    }

    override fun getUser(): Either<Failure, User> {
        return userLocalDataSource
            .getUser()
            .map(UserEntity::toDomain)
    }

    override fun updateToken(token: String): Either<Failure, None> {
        userLocalDataSource.saveToken(token)
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                userRemoteDataSource
                    .updateToken(user.id, token, user.token)
                    .map { None() }
            }
    }

    override fun updateUserLastSeen(): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                userRemoteDataSource
                    .updateUserLastSeen(user.id, user.token, lastSeen = System.currentTimeMillis())
                    .map { None() }
            }
    }

    override fun editUser(user: User): Either<Failure, User> {
        return userLocalDataSource
            .getUser()
            .flatMap {
                userRemoteDataSource
                    .editUser(
                        user.id,
                        user.email,
                        user.name,
                        user.password,
                        user.status,
                        it.token,
                        user.image
                    )
                    .map { response -> response.user.toDomain() }
            }
            .onSuccess { userLocalDataSource.saveUser(user.copy(image = it.image).toEntity()) }
    }
}