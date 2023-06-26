package com.example.chat.presentation.message

import androidx.lifecycle.LiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.message.GetLiveMessagesWithContact
import com.example.chat.domain.message.Message
import javax.inject.Inject

class MessageListViewModel @Inject constructor(
    private val getLiveMessages: GetLiveMessagesWithContact
) : BaseViewModel() {

    fun getMessages(contactId: Long): LiveData<List<Message>> {
        return getLiveMessages(contactId)
    }
}