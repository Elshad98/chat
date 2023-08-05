package com.example.chat.domain.media

import android.graphics.Bitmap
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.core.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class EncodeImageBitmap(
    private val mediaRepository: MediaRepository
) : UseCase<String, EncodeImageBitmap.Params>() {

    override suspend fun run(params: Params): Either<Failure, String> {
        return mediaRepository.encodeImageBitmap(params.bitmap)
    }

    data class Params(
        val bitmap: Bitmap?
    )
}