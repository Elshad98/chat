package com.example.chat.domain.messages

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class GetChats @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<List<MessageEntity>, GetChats.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<MessageEntity>> {
        return messagesRepository.getChats(params.needFetch)
    }

    data class Params(
        val needFetch: Boolean
    )
}