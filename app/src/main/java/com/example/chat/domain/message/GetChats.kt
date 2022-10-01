package com.example.chat.domain.message

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class GetChats @Inject constructor(
    private val messageRepository: MessageRepository
) : UseCase<List<Message>, GetChats.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Message>> {
        return messageRepository.getChats(params.needFetch)
    }

    data class Params(
        val needFetch: Boolean
    )
}