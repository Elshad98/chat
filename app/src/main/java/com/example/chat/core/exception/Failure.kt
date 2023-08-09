package com.example.chat.core.exception

sealed class Failure {

    object AlreadyRequestedFriendError : Failure()
    object NetworkConnectionError : Failure()
    object EmailAlreadyExistError : Failure()
    object EmailNotRegisteredError : Failure()
    object ContactNotFoundError : Failure()
    object AlreadyFriendError : Failure()
    object CantSendEmailError : Failure()
    object NoSavedUsersError : Failure()
    object FilePickError : Failure()
    object ServerError : Failure()
    object TokenError : Failure()
    object AuthError : Failure()
}