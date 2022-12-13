package com.example.chat.data.remote

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.remote.common.ApiParamBuilder
import com.example.chat.data.remote.common.Request
import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.MessagesResponse
import com.example.chat.data.remote.service.MessageService
import javax.inject.Inject

class MessageRemoteDataSource @Inject constructor(
    private val request: Request,
    private val service: MessageService
) {

    fun getChats(userId: Long, token: String): Either<Failure, MessagesResponse> {
        val params = ApiParamBuilder()
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getLastMessages(params))
    }

    fun sendMessage(
        senderId: Long,
        receiverId: Long,
        token: String,
        message: String,
        image: String,
        messageDate: Long
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .messageDate(messageDate)
            .receiverId(receiverId)
            .senderId(senderId)
            .message(message)
            .image(image)
            .token(token)
            .build()
        return request.make(service.sendMessage(params))
    }

    fun getMessagesWithContact(
        contactId: Long,
        userId: Long,
        token: String
    ): Either<Failure, MessagesResponse> {
        val params = ApiParamBuilder()
            .contactId(contactId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getMessagesWithContact(params))
    }

    fun deleteMessageByUser(
        userId: Long,
        messageId: Long,
        token: String
    ): Either<Failure, BaseResponse> {
        val params = ApiParamBuilder()
            .messageId(messageId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.deleteMessageByUser(params))
    }
}