package com.example.chat.domain.message

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetLiveChats @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(): LiveData<List<Message>> {
        return messageRepository.getLiveChats()
    }
}