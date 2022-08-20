package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.messages.GetChats
import com.example.chat.domain.messages.GetMessagesWithContact
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.domain.messages.SendMessage
import com.example.chat.domain.type.None
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val getChatsUseCase: GetChats,
    private val sendMessageUseCase: SendMessage,
    private val getMessagesUseCase: GetMessagesWithContact
) : BaseViewModel() {

    val sendMessageData: MutableLiveData<None> = MutableLiveData()
    val getChatsData: MutableLiveData<List<MessageEntity>> = MutableLiveData()
    val getMessagesData: MutableLiveData<List<MessageEntity>> = MutableLiveData()

    fun getChats(needFetch: Boolean = false) {
        getChatsUseCase(GetChats.Params(needFetch)) { either ->
            either.fold(::handleFailure) { handleGetChats(it, !needFetch) }
        }
    }

    fun getMessages(contactId: Long, needFetch: Boolean = false) {
        getMessagesUseCase(GetMessagesWithContact.Params(contactId, needFetch)) { either ->
            either.fold(::handleFailure) { handleGetMessages(it, contactId, !needFetch) }
        }
    }

    fun sendMessage(toId: Long, message: String, image: String) {
        sendMessageUseCase(SendMessage.Params(toId, message, image)) { either ->
            either.fold(::handleFailure) { handleSendMessage(it, toId) }
        }
    }

    private fun handleGetChats(messages: List<MessageEntity>, fromCache: Boolean) {
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
        messages: List<MessageEntity>,
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
}