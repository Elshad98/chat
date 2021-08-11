package com.example.chat.domain.type

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {

    object NetworkConnectionError : Failure()
    object EmailAlreadyExistError : Failure()
    object NoSavedAccountsError : Failure()
    object ServerError : Failure()
    object TokenError : Failure()
    object AuthError : Failure()
}