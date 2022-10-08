package com.example.chat.data.repository.user

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onNext
import com.example.chat.data.remote.UserRemoteDataSource
import com.example.chat.data.remote.model.dto.toUser
import com.example.chat.domain.user.User
import com.example.chat.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userCache: UserCache,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun login(email: String, password: String): Either<Failure, User> {
        return userCache
            .getToken()
            .flatMap { token ->
                userRemoteDataSource
                    .login(email, password, token)
                    .map { response -> response.user.toUser() }
            }
            .onNext { user ->
                user.password = password
                userCache.saveUser(user)
            }
    }

    override fun logout(): Either<Failure, None> {
        return userCache.logout()
    }

    override fun checkAuth(): Either<Failure, Boolean> {
        return userCache.checkAuth()
    }

    override fun register(email: String, name: String, password: String): Either<Failure, None> {
        return userCache
            .getToken()
            .flatMap { token ->
                userRemoteDataSource
                    .register(
                        email,
                        name,
                        password,
                        token,
                        userDate = System.currentTimeMillis()
                    )
                    .map { None() }
            }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        return userRemoteDataSource
            .forgetPassword(email)
            .map { None() }
    }

    override fun getUser(): Either<Failure, User> {
        return userCache.getUser()
    }

    override fun updateToken(token: String): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                userRemoteDataSource
                    .updateToken(user.id, token, user.token)
                    .map { None() }
                    .onNext { userCache.saveToken(token) }
            }
    }

    override fun updateUserLastSeen(): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                userRemoteDataSource
                    .updateUserLastSeen(
                        user.id,
                        user.token,
                        System.currentTimeMillis()
                    )
                    .map { None() }
            }
    }

    override fun editUser(user: User): Either<Failure, User> {
        return userCache
            .getUser()
            .flatMap {
                userRemoteDataSource
                    .editUser(
                        user.id,
                        user.email,
                        user.name,
                        user.password,
                        user.status,
                        user.token,
                        user.image
                    )
                    .map { response -> response.user.toUser() }
            }
            .onNext {
                user.image = it.image
                userCache.saveUser(user)
            }
    }
}