package com.example.chat.data.repository.message

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onNext
import com.example.chat.data.remote.MessageRemoteDataSource
import com.example.chat.data.remote.model.dto.MessageDto
import com.example.chat.data.remote.model.dto.toMessage
import com.example.chat.data.repository.user.UserCache
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageRemoteDataSource: MessageRemoteDataSource,
    private val messageCache: MessageCache,
    private val userCache: UserCache
) : MessageRepository {

    override fun getChats(needFetch: Boolean): Either<Failure, List<Message>> {
        return userCache
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    messageRemoteDataSource
                        .getChats(user.id, user.token)
                        .map { response -> response.messages.map(MessageDto::toMessage) }
                        .onNext { messages ->
                            messages.map { message ->
                                if (message.senderId == user.id) {
                                    message.fromMe = true
                                }
                            }
                            messageCache.saveMessages(messages)
                        }
                } else {
                    Either.Right(messageCache.getChats())
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
        return userCache
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    messageRemoteDataSource
                        .getMessagesWithContact(contactId, user.id, user.token)
                        .map { response -> response.messages.map(MessageDto::toMessage) }
                        .onNext { messages ->
                            messages.map { message ->
                                if (message.senderId == user.id) {
                                    message.fromMe = true
                                }
                                message.contact = messageCache
                                    .getChats()
                                    .firstOrNull { it.contact?.id == contactId }
                                    ?.contact
                            }
                            messageCache.saveMessages(messages)
                        }
                } else {
                    Either.Right(messageCache.getMessagesWithContact(contactId))
                }
            }
    }

    override fun sendMessage(
        receiverId: Long,
        message: String,
        image: String
    ): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                messageRemoteDataSource
                    .sendMessage(user.id, receiverId, user.token, message, image)
                    .map { None() }
            }
    }

    override fun deleteMessagesByUser(messageId: Long): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                messageCache.deleteMessagesByUser(messageId)
                messageRemoteDataSource
                    .deleteMessagesByUser(user.id, messageId, user.token)
                    .map { None() }
            }
    }
}