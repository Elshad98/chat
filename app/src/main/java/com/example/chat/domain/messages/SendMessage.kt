package com.example.chat.domain.messages

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<None, SendMessage.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return messagesRepository.sendMessage(params.toId, params.message, params.image)
    }

    data class Params(
        val toId: Long,
        val message: String,
        val image: String
    )
}