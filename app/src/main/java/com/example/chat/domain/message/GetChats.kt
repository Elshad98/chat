package com.example.chat.domain.message

import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetChats(
    private val messageRepository: MessageRepository
) : UseCase<List<Message>, None>() {

    override suspend fun run(params: None): Either<Failure, List<Message>> {
        return messageRepository.getChats()
    }
}