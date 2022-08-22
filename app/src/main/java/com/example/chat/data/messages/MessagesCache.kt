package com.example.chat.data.messages

import com.example.chat.domain.messages.MessageEntity

interface MessagesCache {

    fun getChats(): List<MessageEntity>

    fun saveMessage(message: MessageEntity)

    fun deleteMessagesByUser(messageId: Long)

    fun saveMessages(messages: List<MessageEntity>)

    fun getMessagesWithContact(contactId: Long): List<MessageEntity>
}