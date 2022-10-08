package com.example.chat.data.remote.core

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.remote.model.response.BaseResponse
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import retrofit2.Response

@Singleton
class Request @Inject constructor(
    private val networkHandler: NetworkHandler
) {

    fun <T : BaseResponse> make(call: Call<T>): Either<Failure, T> {
        return if (networkHandler.isNetworkAvailable()) {
            execute(call)
        } else {
            Either.Left(Failure.NetworkConnectionError)
        }
    }

    private fun <T : BaseResponse> execute(call: Call<T>): Either<Failure, T> {
        return try {
            val response = call.execute()
            if (response.isSucceed()) {
                Either.Right(response.body()!!)
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
        "There is a user has this email",
        "Email already exists" -> Failure.EmailAlreadyExistError
        "Error in email or password" -> Failure.AuthError
        "Token is invalid" -> Failure.TokenError
        "This contact is already in your friends list" -> Failure.AlreadyFriendError
        "Already found in your friend requests",
        "You requested adding this friend before" -> Failure.AlreadyRequestedFriendError
        "No Contact has this email" -> Failure.ContactNotFoundError
        "This email is not registered before" -> Failure.EmailNotRegisteredError
        "Can't send email to you" -> Failure.CantSendEmailError
        else -> Failure.ServerError
    }
}
