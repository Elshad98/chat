package com.example.chat.domain.message

import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface MessageRepository {

    fun deleteMessagesByUser(messageId: Long): Either<Failure, None>

    fun getChats(needFetch: Boolean): Either<Failure, List<Message>>

    fun getMessagesWithContact(
        contactId: Long,
        needFetch: Boolean
    ): Either<Failure, List<Message>>

    fun sendMessage(
        receiverId: Long,
        message: String,
        image: String
    ): Either<Failure, None>
}