package com.example.chat.data.remote.messages

import com.example.chat.data.remote.core.ApiParamBuilder
import com.example.chat.data.remote.core.Request
import com.example.chat.data.remote.service.AccountService
import com.example.chat.data.remote.service.MessageService
import com.example.chat.data.repository.messages.MessagesRemote
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import javax.inject.Inject
import org.json.JSONArray
import org.json.JSONObject

class MessagesRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: MessageService
) : MessagesRemote {

    override fun getChats(userId: Long, token: String): Either<Failure, List<MessageEntity>> {
        val params = ApiParamBuilder()
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getLastMessages(params)) { it.messages }
    }

    override fun sendMessage(
        senderId: Long,
        receiverId: Long,
        token: String,
        message: String,
        image: String
    ): Either<Failure, None> {
        val params = createSendMessageMap(senderId, receiverId, token, message, image)
        return request.make(service.sendMessage(params)) { None() }
    }

    override fun getMessagesWithContact(
        contactId: Long,
        userId: Long,
        token: String
    ): Either<Failure, List<MessageEntity>> {
        val params = ApiParamBuilder()
            .contactId(contactId)
            .userId(userId)
            .token(token)
            .build()
        return request.make(service.getMessagesWithContact(params)) { it.messages }
    }

    override fun deleteMessagesByUser(
        userId: Long,
        messageId: Long,
        token: String
    ): Either<Failure, None> {
        val params = createDeleteMessagesMap(userId, messageId, token)
        return request.make(service.deleteMessagesByUser(params)) { None() }
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
            map[AccountService.PARAM_IMAGE_NEW] = image
            map[AccountService.PARAM_IMAGE_NEW_NAME] = "user_${fromId}_${time}_photo"
        }
        map[AccountService.PARAM_TOKEN] = token
        map[AccountService.PARAM_MESSAGE] = message
        map[AccountService.PARAM_SENDER_ID] = fromId.toString()
        map[AccountService.PARAM_RECEIVER_ID] = toId.toString()
        map[AccountService.PARAM_MESSAGE_TYPE] = type.toString()
        map[AccountService.PARAM_MESSAGE_DATE] = time.toString()
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
        map[AccountService.PARAM_TOKEN] = token
        map[AccountService.PARAM_USER_ID] = userId.toString()
        map[AccountService.PARAM_MESSAGES_IDS] = itemsArrayObject.toString()
        return map
    }
}