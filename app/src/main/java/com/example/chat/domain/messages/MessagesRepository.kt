package com.example.chat.domain.messages

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface MessagesRepository {

    fun deleteMessagesByUser(messageId: Long): Either<Failure, None>

    fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun getMessagesWithContact(
        contactId: Long,
        needFetch: Boolean
    ): Either<Failure, List<MessageEntity>>

    fun sendMessage(
        receiverId: Long,
        message: String,
        image: String
    ): Either<Failure, None>
}