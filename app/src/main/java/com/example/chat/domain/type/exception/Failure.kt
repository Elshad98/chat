package com.example.chat.domain.type.exception

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {

    object NetworkConnectionError : Failure()
    object ServerError : Failure()
}