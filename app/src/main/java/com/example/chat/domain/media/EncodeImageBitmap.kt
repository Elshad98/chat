package com.example.chat.domain.media

import android.graphics.Bitmap
import com.example.chat.domain.interactor.UseCase
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import javax.inject.Inject

class EncodeImageBitmap @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<String, Bitmap?>() {

    override suspend fun run(params: Bitmap?): Either<Failure, String> {
        return mediaRepository.encodeImageBitmap(params)
    }
}