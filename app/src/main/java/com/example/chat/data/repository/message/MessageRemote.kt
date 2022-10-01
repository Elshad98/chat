package com.example.chat.data.repository.message

import com.example.chat.domain.message.Message
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None

interface MessageRemote {

    fun getChats(userId: Long, token: String): Either<Failure, List<Message>>

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
    ): Either<Failure, List<Message>>
}