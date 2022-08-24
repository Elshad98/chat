package com.example.chat.remote.messages

import com.example.chat.data.messages.MessagesRemote
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.remote.core.Request
import com.example.chat.remote.service.ApiService
import javax.inject.Inject
import org.json.JSONArray
import org.json.JSONObject

class MessagesRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: ApiService
) : MessagesRemote {

    override fun getChats(userId: Long, token: String): Either<Failure, List<MessageEntity>> {
        val params = createGetLastMessagesMap(userId, token)
        return request.make(service.getLastMessages(params)) { it.messages }
    }

    override fun sendMessage(
        fromId: Long,
        toId: Long,
        token: String,
        message: String,
        image: String
    ): Either<Failure, None> {
        val params = createSendMessageMap(fromId, toId, token, message, image)
        return request.make(service.sendMessages(params)) { None() }
    }

    override fun getMessagesWithContact(
        contactId: Long,
        userId: Long,
        token: String
    ): Either<Failure, List<MessageEntity>> {
        val params = createGetMessagesWithContactMap(contactId, userId, token)
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

    private fun createGetLastMessagesMap(
        userId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        return map
    }

    private fun createGetMessagesWithContactMap(
        contactId: Long,
        userId: Long,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        map[ApiService.PARAM_CONTACT_ID] = contactId.toString()
        return map
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
            map[ApiService.PARAM_IMAGE_NEW] = image
            map[ApiService.PARAM_IMAGE_NEW_NAME] = "user_${fromId}_${time}_photo"
        }
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_MESSAGE] = message
        map[ApiService.PARAM_SENDER_ID] = fromId.toString()
        map[ApiService.PARAM_RECEIVER_ID] = toId.toString()
        map[ApiService.PARAM_MESSAGE_TYPE] = type.toString()
        map[ApiService.PARAM_MESSAGE_DATE] = time.toString()
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
        map[ApiService.PARAM_TOKEN] = token
        map[ApiService.PARAM_USER_ID] = userId.toString()
        map[ApiService.PARAM_MESSAGES_IDS] = itemsArrayObject.toString()
        return map
    }
}