package com.example.chat.domain.media

import android.graphics.Bitmap
import android.net.Uri
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import com.example.chat.domain.interactor.UseCase
import toothpick.InjectConstructor

@InjectConstructor
class GetPickedImage(
    private val mediaRepository: MediaRepository
) : UseCase<Bitmap, Uri?>() {

    override suspend fun run(params: Uri?): Either<Failure, Bitmap> {
        return mediaRepository.getPickedImage(params)
    }
}