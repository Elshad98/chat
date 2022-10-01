package com.example.chat.data.repository.user

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.type.flatMap
import com.example.chat.domain.type.onNext
import com.example.chat.domain.user.User
import com.example.chat.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userCache: UserCache,
    private val userRemote: UserRemote
) : UserRepository {

    override fun login(email: String, password: String): Either<Failure, User> {
        return userCache
            .getToken()
            .flatMap { token -> userRemote.login(email, password, token) }
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
                userRemote.register(
                    email,
                    name,
                    password,
                    token,
                    userDate = System.currentTimeMillis()
                )
            }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        return userRemote.forgetPassword(email)
    }

    override fun getUser(): Either<Failure, User> {
        return userCache.getUser()
    }

    override fun updateToken(token: String): Either<Failure, None> {
        userCache.saveToken(token)

        return userCache
            .getUser()
            .flatMap { user -> userRemote.updateToken(user.id, token, user.token) }
    }

    override fun updateUserLastSeen(): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                userRemote.updateUserLastSeen(
                    user.id,
                    user.token,
                    System.currentTimeMillis()
                )
            }
    }

    override fun editUser(user: User): Either<Failure, User> {
        return userCache
            .getUser()
            .flatMap {
                userRemote.editUser(
                    user.id,
                    user.email,
                    user.name,
                    user.password,
                    user.status,
                    user.token,
                    user.image
                )
            }
            .onNext {
                user.image = it.image
                userCache.saveUser(user)
            }
    }
}