package com.example.chat.domain.media

import android.net.Uri
import com.example.chat.domain.interactor.UseCase
import com.example.chat.core.functional.Either
import com.example.chat.core.exception.Failure
import com.example.chat.core.None
import javax.inject.Inject

class CreateImageFile @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<Uri, None>() {

    override suspend fun run(params: None): Either<Failure, Uri> = mediaRepository.createImageFile()
}