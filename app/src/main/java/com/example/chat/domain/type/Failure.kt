package com.example.chat.domain.type

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {

    object AlreadyRequestedFriendError : Failure()
    object NetworkConnectionError : Failure()
    object EmailAlreadyExistError : Failure()
    object EmailNotRegisteredError : Failure()
    object ContactNotFoundError : Failure()
    object NoSavedAccountsError : Failure()
    object AlreadyFriendError : Failure()
    object CantSendEmailError : Failure()
    object FilePickError : Failure()
    object ServerError : Failure()
    object TokenError : Failure()
    object AuthError : Failure()
}