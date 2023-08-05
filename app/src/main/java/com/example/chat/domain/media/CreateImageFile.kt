package com.example.chat.domain.media

import android.net.Uri
import com.example.chat.core.None
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class CreateImageFile(
    private val mediaRepository: MediaRepository
) : UseCase<Uri, None>() {

    override suspend fun run(params: None): Either<Failure, Uri> {
        return mediaRepository.createImageFile()
    }
}