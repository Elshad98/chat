package com.example.chat.domain.message

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetChats(
    private val messageRepository: MessageRepository
) : UseCase<List<Message>, GetChats.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Message>> {
        return messageRepository.getChats(params.needFetch)
    }

    data class Params(
        val needFetch: Boolean
    )
}