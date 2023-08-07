package com.example.chat.data.repository

import androidx.lifecycle.LiveData
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.map
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onSuccess
import com.example.chat.data.local.MessageLocalDataSource
import com.example.chat.data.local.UserLocalDataSource
import com.example.chat.data.local.model.MessageEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.data.local.model.toEntity
import com.example.chat.data.remote.MessageRemoteDataSource
import com.example.chat.data.remote.model.dto.MessageDto
import com.example.chat.data.remote.model.dto.toDomain
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageRepository
import toothpick.InjectConstructor

@InjectConstructor
class MessageRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val messageLocalDataSource: MessageLocalDataSource,
    private val messageRemoteDataSource: MessageRemoteDataSource
) : MessageRepository {

    override fun getChats(): Either<Failure, List<Message>> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                messageRemoteDataSource.getChats(user.id, user.token)
            }
            .map { response ->
                response.messages.map(MessageDto::toDomain)
            }
            .onSuccess { messages ->
                messageLocalDataSource.saveMessages(messages.map(Message::toEntity))
            }
    }

    override fun getLiveMessagesWithContact(contactId: Long): LiveData<List<Message>> {
        return messageLocalDataSource
            .getLiveMessagesWithContact(contactId)
            .map { messages ->
                messages.map(MessageEntity::toDomain)
            }
    }

    override fun getMessagesWithContact(contactId: Long): Either<Failure, List<Message>> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                messageRemoteDataSource.getMessagesWithContact(contactId, user.id, user.token)
            }
            .map { response ->
                response.messages.map(MessageDto::toDomain)
            }
            .onSuccess { messages ->
                messageLocalDataSource.saveMessages(messages.map(Message::toEntity))
            }
    }

    override fun sendMessage(
        receiverId: Long,
        message: String,
        image: String
    ): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                messageRemoteDataSource
                    .sendMessage(user.id, receiverId, user.token, message, image, System.currentTimeMillis())
            }
            .map { None() }
    }

    override fun getLiveChats(): LiveData<List<Message>> {
        return messageLocalDataSource
            .getLiveChats()
            .map { messages ->
                messages
                    .distinctBy { it.contact.id }
                    .map(MessageEntity::toDomain)
            }
    }

    override fun deleteMessageByUser(messageId: Long): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                messageRemoteDataSource.deleteMessageByUser(user.id, messageId, user.token)
            }
            .map { None() }
            .onSuccess {
                messageLocalDataSource.deleteMessageByUser(messageId)
            }
    }
}