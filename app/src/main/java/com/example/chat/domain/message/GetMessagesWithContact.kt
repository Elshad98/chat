package com.example.chat.domain.message

import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class GetMessagesWithContact @Inject constructor(
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