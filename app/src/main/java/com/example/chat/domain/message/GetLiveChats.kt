package com.example.chat.domain.message

import androidx.lifecycle.LiveData
import toothpick.InjectConstructor

@InjectConstructor
class GetLiveChats(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(): LiveData<List<Message>> {
        return messageRepository.getLiveChats()
    }
}