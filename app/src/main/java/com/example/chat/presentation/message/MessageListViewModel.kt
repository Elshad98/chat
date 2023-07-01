package com.example.chat.presentation.message

import androidx.lifecycle.LiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.message.GetLiveMessagesWithContact
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.SendMessage
import javax.inject.Inject

class MessageListViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    private val getLiveMessages: GetLiveMessagesWithContact
) : BaseViewModel() {

    override fun onCleared() {
        super.onCleared()
        sendMessage.unsubscribe()
    }

    fun sendMessage(receiverId: Long, message: String, image: String) {
        if (message.isEmpty()) {
            return
        }
        sendMessage(SendMessage.Params(receiverId, message, image)) { either ->
            either.fold(::handleFailure) { }
        }
    }

    fun getMessages(contactId: Long): LiveData<List<Message>> {
        return getLiveMessages(contactId)
    }
}