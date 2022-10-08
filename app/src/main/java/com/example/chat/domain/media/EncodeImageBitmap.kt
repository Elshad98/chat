package com.example.chat.domain.media

import android.graphics.Bitmap
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import javax.inject.Inject

class EncodeImageBitmap @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<String, Bitmap?>() {

    override suspend fun run(params: Bitmap?): Either<Failure, String> {
        return mediaRepository.encodeImageBitmap(params)
    }
}