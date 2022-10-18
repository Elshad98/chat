package com.example.chat.data.repository

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
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
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val messageLocalDataSource: MessageLocalDataSource,
    private val messageRemoteDataSource: MessageRemoteDataSource
) : MessageRepository {

    override fun getChats(needFetch: Boolean): Either<Failure, List<Message>> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    messageRemoteDataSource
                        .getChats(user.id, user.token)
                        .map { response -> response.messages.map(MessageDto::toDomain) }
                        .onSuccess { messages ->
                            messages.map { message ->
                                if (message.senderId == user.id) {
                                    message.fromMe = true
                                }
                            }
                            messageLocalDataSource.saveMessages(messages.map(Message::toEntity))
                        }
                } else {
                    val messages = messageLocalDataSource
                        .getChats()
                        .map(MessageEntity::toDomain)
                    Either.Right(messages)
                }
            }
            .map { messages ->
                messages.distinctBy { message ->
                    message.contact?.id
                }
            }
    }

    override fun getMessagesWithContact(
        contactId: Long,
        needFetch: Boolean
    ): Either<Failure, List<Message>> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    messageRemoteDataSource
                        .getMessagesWithContact(contactId, user.id, user.token)
                        .map { response -> response.messages.map(MessageDto::toDomain) }
                        .onSuccess { messages ->
                            messages.map { message ->
                                if (message.senderId == user.id) {
                                    message.fromMe = true
                                }
                                message.contact = messageLocalDataSource
                                    .getChats()
                                    .map(MessageEntity::toDomain)
                                    .firstOrNull { it.contact?.id == contactId }
                                    ?.contact
                            }
                            messageLocalDataSource.saveMessages(messages.map(Message::toEntity))
                        }
                } else {
                    val messages = messageLocalDataSource
                        .getMessagesWithContact(contactId)
                        .map(MessageEntity::toDomain)
                    Either.Right(messages)
                }
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
                    .sendMessage(user.id, receiverId, user.token, message, image)
                    .map { None() }
            }
    }

    override fun deleteMessageByUser(messageId: Long): Either<Failure, None> {
        return userLocalDataSource
            .getUser()
            .flatMap { user ->
                messageRemoteDataSource
                    .deleteMessageByUser(user.id, messageId, user.token)
                    .map { None() }
                    .onSuccess { messageLocalDataSource.deleteMessageByUser(messageId) }
            }
    }
}