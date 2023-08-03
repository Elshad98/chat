package com.example.chat.domain.message

import androidx.lifecycle.LiveData
import toothpick.InjectConstructor

@InjectConstructor
class GetLiveMessagesWithContact(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(contactId: Long): LiveData<List<Message>> {
        return messageRepository.getLiveMessagesWithContact(contactId)
    }
}