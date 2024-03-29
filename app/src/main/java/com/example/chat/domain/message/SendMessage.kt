package com.example.chat.domain.message

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class SendMessage(
    private val messageRepository: MessageRepository
) : UseCase<None, SendMessage.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return messageRepository.sendMessage(params.receiverId, params.message, params.image)
    }

    data class Params(
        val receiverId: Long,
        val message: String,
        val image: String
    )
}