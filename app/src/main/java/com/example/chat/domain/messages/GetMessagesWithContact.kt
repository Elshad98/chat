package com.example.chat.domain.messages

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class GetMessagesWithContact @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<List<MessageEntity>, GetMessagesWithContact.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<MessageEntity>> {
        return messagesRepository.getMessagesWithContact(params.contactId, params.needFetch)
    }

    data class Params(
        val contactId: Long,
        val needFetch: Boolean
    )
}