package com.example.chat.domain.media

import android.graphics.Bitmap
import android.net.Uri
import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either

interface MediaRepository {

    fun createImageFile(): Either<Failure, Uri>

    fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String>

    fun getPickedImage(uri: Uri?): Either<Failure, Bitmap>
}