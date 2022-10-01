package com.example.chat.data.repository.message

import com.example.chat.data.repository.user.UserCache
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageRepository
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.type.flatMap
import com.example.chat.domain.type.map
import com.example.chat.domain.type.onNext
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageRemote: MessageRemote,
    private val messageCache: MessageCache,
    private val userCache: UserCache
) : MessageRepository {

    override fun getChats(needFetch: Boolean): Either<Failure, List<Message>> {
        return userCache
            .getUser()
            .flatMap { user ->
                if (needFetch) {
                    messageRemote
                        .getChats(user.id, user.token)
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
                    messageRemote
                        .getMessagesWithContact(contactId, user.id, user.token)
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
                messageRemote.sendMessage(user.id, receiverId, user.token, message, image)
            }
    }

    override fun deleteMessagesByUser(messageId: Long): Either<Failure, None> {
        return userCache
            .getUser()
            .flatMap { user ->
                messageCache.deleteMessagesByUser(messageId)
                messageRemote.deleteMessagesByUser(user.id, messageId, user.token)
            }
    }
}