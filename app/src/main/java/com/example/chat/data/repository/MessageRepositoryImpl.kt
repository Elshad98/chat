package com.example.chat.data.repository

import androidx.lifecycle.LiveData
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.map
import com.example.chat.core.functional.Either
import com.example.chat.core.functional.flatMap
import com.example.chat.core.functional.map
import com.example.chat.core.functional.onSuccess
import com.example.chat.data.local.UserStorage
import com.example.chat.data.local.dao.MessageDao
import com.example.chat.data.local.model.MessageEntity
import com.example.chat.data.local.model.toDomain
import com.example.chat.data.local.model.toEntity
import com.example.chat.data.remote.common.ApiParamBuilder
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.model.dto.MessageDto
import com.example.chat.data.remote.model.dto.toDomain
import com.example.chat.data.remote.service.MessageService
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageRepository
import toothpick.InjectConstructor

@InjectConstructor
class MessageRepositoryImpl(
    private val request: Request,
    private val messageDao: MessageDao,
    private val userStorage: UserStorage,
    private val messageService: MessageService
) : MessageRepository {

    override fun getChats(): Either<Failure, List<Message>> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(messageService.getLastMessages(params))
            }
            .map { response ->
                response.messages.map(MessageDto::toDomain)
            }
            .onSuccess { messages ->
                messageDao.saveMessages(messages.map(Message::toEntity))
            }
    }

    override fun getLiveMessagesWithContact(contactId: Long): LiveData<List<Message>> {
        return messageDao
            .getLiveMessagesWithContact(contactId)
            .map { messages ->
                messages.map(MessageEntity::toDomain)
            }
    }

    override fun getMessagesWithContact(contactId: Long): Either<Failure, List<Message>> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .contactId(contactId)
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(messageService.getMessagesWithContact(params))
            }
            .map { response ->
                response.messages.map(MessageDto::toDomain)
            }
            .onSuccess { messages ->
                messageDao.saveMessages(messages.map(Message::toEntity))
            }
    }

    override fun sendMessage(
        receiverId: Long,
        message: String,
        image: String
    ): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .messageDate(System.currentTimeMillis())
                    .receiverId(receiverId)
                    .token(user.token)
                    .senderId(user.id)
                    .message(message)
                    .image(image)
                    .build()
                request.make(messageService.sendMessage(params))
            }
            .map { None() }
    }

    override fun getLiveChats(): LiveData<List<Message>> {
        return messageDao
            .getLiveChats()
            .map { messages ->
                messages
                    .distinctBy { it.contact.id }
                    .map(MessageEntity::toDomain)
            }
    }

    override fun deleteMessageByUser(messageId: Long): Either<Failure, None> {
        return userStorage
            .getUser()
            .flatMap { user ->
                val params = ApiParamBuilder()
                    .messageId(messageId)
                    .token(user.token)
                    .userId(user.id)
                    .build()
                request.make(messageService.deleteMessageByUser(params))
            }
            .map { None() }
            .onSuccess {
                messageDao.deleteMessageByUser(messageId)
            }
    }
}