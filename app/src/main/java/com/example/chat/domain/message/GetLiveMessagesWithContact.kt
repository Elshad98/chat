package com.example.chat.domain.message

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetLiveMessagesWithContact @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(contactId: Long): LiveData<List<Message>> {
        return messageRepository.getLiveMessagesWithContact(contactId)
    }
}