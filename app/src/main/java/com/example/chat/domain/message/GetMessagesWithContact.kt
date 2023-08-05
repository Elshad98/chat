package com.example.chat.domain.message

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetMessagesWithContact(
    private val messageRepository: MessageRepository
) : UseCase<List<Message>, GetMessagesWithContact.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Message>> {
        return messageRepository.getMessagesWithContact(params.contactId, params.needFetch)
    }

    data class Params(
        val contactId: Long,
        val needFetch: Boolean
    )
}