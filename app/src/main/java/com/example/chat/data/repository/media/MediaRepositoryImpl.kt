package com.example.chat.data.repository.media

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.chat.domain.media.MediaRepository
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import java.io.File
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val context: Context
) : MediaRepository {

    companion object {

        private const val DEFAULT_BITMAP_QUALITY = 100
    }

    override fun createImageFile(): Either<Failure, Uri> {
        val uri = MediaHelper.createImageFile(context)
        return if (uri == null) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(uri)
        }
    }

    override fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String> {
        if (bitmap == null) {
            return Either.Left(Failure.FilePickError)
        }

        val image = MediaHelper.encodeToBase64(
            bitmap,
            Bitmap.CompressFormat.JPEG,
            DEFAULT_BITMAP_QUALITY
        )

        return if (image.isEmpty()) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(image)
        }
    }

    override fun getPickedImage(uri: Uri?): Either<Failure, Bitmap> {
        if (uri == null) {
            return Either.Left(Failure.FilePickError)
        }

        val file = File(MediaHelper.getPath(context, uri))
        val image = MediaHelper.saveBitmapToFile(file)

        return if (image == null) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(image)
        }
    }
}