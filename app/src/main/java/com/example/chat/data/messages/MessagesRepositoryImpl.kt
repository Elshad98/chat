package com.example.chat.data.messages

import com.example.chat.data.account.AccountCache
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.domain.messages.MessagesRepository
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.type.flatMap
import com.example.chat.domain.type.map
import com.example.chat.domain.type.onNext

class MessagesRepositoryImpl(
    private val messagesRemote: MessagesRemote,
    private val messagesCache: MessagesCache,
    private val accountCache: AccountCache
) : MessagesRepository {

    override fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                if (needFetch) {
                    messagesRemote
                        .getChats(account.id, account.token)
                        .onNext { messages ->
                            messages.map { message ->
                                if (message.senderId == account.id) {
                                    message.fromMe = true
                                }
                                messagesCache.saveMessage(message)
                            }
                        }
                } else {
                    Either.Right(messagesCache.getChats())
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
    ): Either<Failure, List<MessageEntity>> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                if (needFetch) {
                    messagesRemote
                        .getMessagesWithContact(contactId, account.id, account.token)
                        .onNext { messages ->
                            messages.map { message ->
                                if (message.senderId == account.id) {
                                    message.fromMe = true
                                }
                                message.contact = messagesCache
                                    .getChats()
                                    .first { it.contact?.id == contactId }
                                    .contact
                                messagesCache.saveMessage(message)
                            }
                        }
                } else {
                    Either.Right(messagesCache.getMessagesWithContact(contactId))
                }
            }
    }

    override fun sendMessage(toId: Long, message: String, image: String): Either<Failure, None> {
        return accountCache
            .getCurrentAccount()
            .flatMap { account ->
                messagesRemote.sendMessage(account.id, toId, account.token, message, image)
            }
    }
}