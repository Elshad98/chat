package com.example.chat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.domain.message.DeleteMessage
import com.example.chat.domain.message.GetChats
import com.example.chat.domain.message.GetLiveChats
import com.example.chat.domain.message.GetLiveMessagesWithContact
import com.example.chat.domain.message.GetMessagesWithContact
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.SendMessage
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val getChatsUseCase: GetChats,
    private val sendMessageUseCase: SendMessage,
    private val getLiveChatsUseCase: GetLiveChats,
    private val deleteMessageUseCase: DeleteMessage,
    private val getMessagesUseCase: GetMessagesWithContact,
    private val getLiveMessagesUseCase: GetLiveMessagesWithContact
) : BaseViewModel() {

    val chatList = getLiveChatsUseCase()
    val sendMessageData: MutableLiveData<None> = MutableLiveData()
    val deleteMessageData: MutableLiveData<None> = MutableLiveData()
    val getChatsData: MutableLiveData<List<Message>> = MutableLiveData()
    val getMessagesData: MutableLiveData<List<Message>> = MutableLiveData()

    fun getChats(needFetch: Boolean = false) {
        getChatsUseCase(GetChats.Params(needFetch)) { either ->
            either.fold(::handleFailure) { handleGetChats(it, !needFetch) }
        }
    }

    fun getMessagesData(contactId: Long): LiveData<List<Message>> {
        return getLiveMessagesUseCase(contactId)
    }

    fun getMessages(contactId: Long, needFetch: Boolean = false) {
        getMessagesUseCase(GetMessagesWithContact.Params(contactId, needFetch)) { either ->
            either.fold(::handleFailure) { handleGetMessages(it, contactId, !needFetch) }
        }
    }

    fun sendMessage(receiverId: Long, message: String, image: String) {
        sendMessageUseCase(SendMessage.Params(receiverId, message, image)) { either ->
            either.fold(::handleFailure) { handleSendMessage(it, receiverId) }
        }
    }

    fun deleteMessage(contactId: Long, messageId: Long) {
        deleteMessageUseCase(DeleteMessage.Params(messageId)) { either ->
            either.fold(::handleFailure) { handleDeleteMessage(contactId, it) }
        }
    }

    private fun handleGetChats(messages: List<Message>, fromCache: Boolean) {
        getChatsData.value = messages
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getChats(true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getChatsUseCase.unsubscribe()
        getMessagesUseCase.unsubscribe()
        sendMessageUseCase.unsubscribe()
    }

    private fun handleGetMessages(
        messages: List<Message>,
        contactId: Long,
        fromCache: Boolean
    ) {
        getMessagesData.value = messages
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getMessages(contactId, true)
        }
    }

    private fun handleSendMessage(none: None, contactId: Long) {
        sendMessageData.value = none
        getMessages(contactId, true)
    }

    private fun handleDeleteMessage(contactId: Long, none: None) {
        deleteMessageData.value = none
        getMessages(contactId, true)
    }
}