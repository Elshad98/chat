package com.example.chat.data.repository.message

import com.example.chat.domain.message.Message

interface MessageCache {

    fun getChats(): List<Message>

    fun saveMessage(message: Message)

    fun deleteMessagesByUser(messageId: Long)

    fun saveMessages(messages: List<Message>)

    fun getMessagesWithContact(contactId: Long): List<Message>
}