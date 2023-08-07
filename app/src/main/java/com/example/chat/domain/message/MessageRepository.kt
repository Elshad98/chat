package com.example.chat.domain.message

import androidx.lifecycle.LiveData
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either

interface MessageRepository {

    fun getLiveChats(): LiveData<List<Message>>

    fun getChats(): Either<Failure, List<Message>>

    fun deleteMessageByUser(messageId: Long): Either<Failure, None>

    fun getLiveMessagesWithContact(contactId: Long): LiveData<List<Message>>

    fun getMessagesWithContact(contactId: Long): Either<Failure, List<Message>>

    fun sendMessage(receiverId: Long, message: String, image: String): Either<Failure, None>
}