package com.example.chat.domain.messages

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface MessagesRepository {

    fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun getMessagesWithContact(
        contactId: Long,
        needFetch: Boolean
    ): Either<Failure, List<MessageEntity>>

    fun sendMessage(
        toId: Long,
        message: String,
        image: String
    ): Either<Failure, None>
}