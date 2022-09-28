package com.example.chat.data.repository.messages

import com.example.chat.domain.messages.MessageEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface MessagesRemote {

    fun getChats(userId: Long, token: String): Either<Failure, List<MessageEntity>>

    fun deleteMessagesByUser(userId: Long, messageId: Long, token: String): Either<Failure, None>

    fun sendMessage(
        senderId: Long,
        receiverId: Long,
        token: String,
        message: String,
        image: String
    ): Either<Failure, None>

    fun getMessagesWithContact(
        contactId: Long,
        userId: Long,
        token: String
    ): Either<Failure, List<MessageEntity>>
}