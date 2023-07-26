package com.example.chat.data.repository.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.chat.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File

object MediaHelper {

    fun encodeToBase64(
        bitmap: Bitmap,
        compressFormat: Bitmap.CompressFormat,
        quality: Int
    ): String {
        ByteArrayOutputStream().use { outputStream ->
            bitmap.compress(compressFormat, quality, outputStream)
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        }
    }

    fun createImageFile(context: Context): Uri? {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "IMG_${System.currentTimeMillis()}.png"
        )

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", file)
        } else {
            file.toUri()
        }
    }

    fun getPath(context: Context, uri: Uri): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.contentResolver.openFileDescriptor(uri, "r")?.use {
                val fileDescriptor = it.fileDescriptor
                val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                return getAbsolutePath(context, getImageUri(context, image))
            }
        } else {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (uri.isExternalStorageDocument) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":")
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                    }
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                return if (uri.isGooglePhotosUri) {
                    uri.lastPathSegment
                } else {
                    getAbsolutePath(context, uri)
                }
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
        }
        return null
    }

    fun saveBitmapToFile(file: File): Bitmap? {
        val options1 = BitmapFactory.Options()
        options1.inJustDecodeBounds = true
        options1.inSampleSize = 6

        file.inputStream().use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, options1)
        }

        var scale = 1
        val requiredSize = 75
        while (options1.outWidth / scale / 2 >= requiredSize && options1.outHeight / scale / 2 >= requiredSize) {
            scale *= 2
        }

        val options2 = BitmapFactory.Options()
        options2.inSampleSize = scale

        val selectedBitmap = file.inputStream().use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, options2)
        }

        file.createNewFile()
        file.outputStream().use { outputStream ->
            return selectedBitmap?.apply {
                compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        }
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        ByteArrayOutputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Image", null)
        return path.toUri()
    }

    private fun getAbsolutePath(
        context: Context,
        uri: Uri?,
        selection: String? = null,
        selectionArgs: Array<String>? = null
    ): String? {
        if (uri == null) {
            return null
        }

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        return context.contentResolver
            .query(uri, projection, selection, selectionArgs, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.getString(columnIndex)
                } else {
                    null
                }
            }
    }
}

private val Uri.isExternalStorageDocument: Boolean
    get() = authority == "com.android.externalstorage.documents"

private val Uri.isMediaDocument: Boolean
    get() = authority == "com.android.providers.media.documents"

private val Uri.isGooglePhotosUri: Boolean
    get() = authority == "com.google.android.apps.photos.content"

private val Uri.isDownloadsDocument: Boolean
    get() = authority == "com.android.providers.downloads.documents"