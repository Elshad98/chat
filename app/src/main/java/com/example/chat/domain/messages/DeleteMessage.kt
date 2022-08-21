package com.example.chat.domain.messages

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class DeleteMessage @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<None, DeleteMessage.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return messagesRepository.deleteMessagesByUser(params.messageId)
    }

    data class Params(val messageId: Long)
}