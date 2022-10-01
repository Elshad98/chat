package com.example.chat.domain.message

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject

class DeleteMessage @Inject constructor(
    private val messageRepository: MessageRepository
) : UseCase<None, DeleteMessage.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return messageRepository.deleteMessagesByUser(params.messageId)
    }

    data class Params(val messageId: Long)
}