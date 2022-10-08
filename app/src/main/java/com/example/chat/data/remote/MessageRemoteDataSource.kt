package com.example.chat.data.remote

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.data.remote.core.ApiParamBuilder
import com.example.chat.data.remote.core.Request
import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.MessagesResponse
import com.example.chat.data.remote.service.MessageService
import com.example.chat.data.remote.service.UserService
import javax.inject.Inject
import org.json.JSONArray
import org.json.JSONObject

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
        image: String
    ): Either<Failure, BaseResponse> {
        val params = createSendMessageMap(senderId, receiverId, token, message, image)
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

    fun deleteMessagesByUser(
        userId: Long,
        messageId: Long,
        token: String
    ): Either<Failure, BaseResponse> {
        val params = createDeleteMessagesMap(userId, messageId, token)
        return request.make(service.deleteMessagesByUser(params))
    }

    private fun createSendMessageMap(
        fromId: Long,
        toId: Long,
        token: String,
        message: String,
        image: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        val time = System.currentTimeMillis()
        var type = 1

        if (image.isNotBlank()) {
            type = 2
            map[UserService.PARAM_IMAGE_NEW] = image
            map[UserService.PARAM_IMAGE_NEW_NAME] = "user_${fromId}_${time}_photo"
        }
        map[UserService.PARAM_TOKEN] = token
        map[UserService.PARAM_MESSAGE] = message
        map[UserService.PARAM_SENDER_ID] = fromId.toString()
        map[UserService.PARAM_RECEIVER_ID] = toId.toString()
        map[UserService.PARAM_MESSAGE_TYPE] = type.toString()
        map[UserService.PARAM_MESSAGE_DATE] = time.toString()
        return map
    }

    private fun createDeleteMessagesMap(
        userId: Long,
        messageId: Long,
        token: String
    ): Map<String, String> {
        val itemsArrayObject = JSONObject()
        val itemsArray = JSONArray()
        val itemObject = JSONObject()

        itemObject.put("message_id", messageId)
        itemsArray.put(itemObject)
        itemsArrayObject.put("messages", itemsArray)

        val map = HashMap<String, String>()
        map[UserService.PARAM_TOKEN] = token
        map[UserService.PARAM_USER_ID] = userId.toString()
        map[UserService.PARAM_MESSAGES_IDS] = itemsArrayObject.toString()
        return map
    }
}