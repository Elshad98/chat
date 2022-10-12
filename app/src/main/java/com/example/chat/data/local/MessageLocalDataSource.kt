package com.example.chat.data.local

import com.example.chat.data.local.dao.MessageDao
import com.example.chat.data.local.model.MessageEntity
import javax.inject.Inject

class MessageLocalDataSource @Inject constructor(
    private val messageDao: MessageDao
) {

    fun getChats(): List<MessageEntity> {
        return messageDao.getChats()
    }

    fun saveMessage(message: MessageEntity) {
        messageDao.saveMessage(message)
    }

    fun deleteMessageByUser(messageId: Long) {
        messageDao.deleteMessageByUser(messageId)
    }

    fun saveMessages(messages: List<MessageEntity>) {
        messageDao.saveMessages(messages)
    }

    fun getMessagesWithContact(contactId: Long): List<MessageEntity> {
        return messageDao.getMessagesWithContact(contactId)
    }
}