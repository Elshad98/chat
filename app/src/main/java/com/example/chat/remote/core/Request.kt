package com.example.chat.remote.core

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import retrofit2.Response

@Singleton
class Request @Inject constructor(
    private val networkHandler: NetworkHandler
) {

    fun <T : BaseResponse, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return if (networkHandler.isNetworkAvailable()) {
            execute(call, transform)
        } else {
            Either.Left(Failure.NetworkConnectionError)
        }
    }

    private fun <T : BaseResponse, R> execute(
        call: Call<T>,
        transform: (T) -> R
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            if (response.isSucceed()) {
                Either.Right(transform(response.body()!!))
            } else {
                Either.Left(response.parseError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}

private fun <T : BaseResponse> Response<T>.isSucceed(): Boolean {
    return isSuccessful && body() != null && (body() as BaseResponse).success == 1
}

private fun <T : BaseResponse> Response<T>.parseError(): Failure {
    return when ((body() as BaseResponse).message) {
        "email already exists" -> Failure.EmailAlreadyExistError
        "error in email or password" -> Failure.AuthError
        "Token is invalid" -> Failure.TokenError
        "this contact is already in your friends list" -> Failure.AlreadyFriendError
        "already found in your friend requests",
        "you requested adding this friend before" -> Failure.AlreadyRequestedFriendError
        "No Contact has this email" -> Failure.ContactNotFoundError
        else -> Failure.ServerError
    }
}
