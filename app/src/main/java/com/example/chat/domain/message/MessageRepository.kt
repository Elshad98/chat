package com.example.chat.domain.message

import androidx.lifecycle.LiveData
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either

interface MessageRepository {

    fun getLiveChats(): LiveData<List<Message>>

    fun deleteMessageByUser(messageId: Long): Either<Failure, None>

    fun getChats(needFetch: Boolean): Either<Failure, List<Message>>

    fun getLiveMessagesWithContact(contactId: Long): LiveData<List<Message>>

    fun sendMessage(receiverId: Long, message: String, image: String): Either<Failure, None>

    fun getMessagesWithContact(contactId: Long, needFetch: Boolean): Either<Failure, List<Message>>
}