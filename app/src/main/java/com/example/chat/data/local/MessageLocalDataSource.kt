package com.example.chat.data.local

import androidx.lifecycle.LiveData
import com.example.chat.data.local.dao.MessageDao
import com.example.chat.data.local.model.MessageEntity
import javax.inject.Inject

class MessageLocalDataSource @Inject constructor(
    private val messageDao: MessageDao
) {

    fun deleteMessageByUser(messageId: Long) {
        messageDao.deleteMessageByUser(messageId)
    }

    fun saveMessages(messages: List<MessageEntity>) {
        messageDao.saveMessages(messages)
    }

    fun getChats(): List<MessageEntity> {
        return messageDao.getChats()
    }

    fun getMessagesWithContact(contactId: Long): List<MessageEntity> {
        return messageDao.getMessagesWithContact(contactId)
    }

    fun getLiveMessagesWithContact(contactId: Long): LiveData<List<MessageEntity>> {
        return messageDao.getLiveMessagesWithContact(contactId)
    }
}