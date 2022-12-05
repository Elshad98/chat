package com.example.chat.data.repository.media

import android.content.ContentUris
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
import java.io.FileInputStream
import java.io.FileOutputStream

object MediaHelper {

    fun encodeToBase64(
        bitmap: Bitmap,
        compressFormat: Bitmap.CompressFormat,
        quality: Int
    ): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(compressFormat, quality, stream)
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
    }

    fun createImageFile(context: Context): Uri? {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            "IMG_${System.currentTimeMillis()}.png"
        )

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } else {
            file.toUri()
        }
    }

    fun getPath(context: Context, uri: Uri): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val mInputPFD = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
            val fileDescriptor = mInputPFD.fileDescriptor

            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            val tempUri = getImageUri(context, image)

            return getAbsolutePath(context, tempUri)
        } else {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (uri.isExternalStorageDocument) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                    }
                } else if (uri.isDownloadsDocument) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        "content://downloads/public_downloads".toUri(),
                        id.toLong()
                    )
                    return getAbsolutePath(context, contentUri)
                } else if (uri.isMediaDocument) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()

                    val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getAbsolutePath(
                        context,
                        contentUri,
                        selection,
                        selectionArgs
                    )
                }
            } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
                return if (uri.isGooglePhotosUri) {
                    uri.lastPathSegment
                } else {
                    getAbsolutePath(context, uri)
                }
            } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
                return uri.path
            }
        }
        return null
    }

    fun saveBitmapToFile(file: File): Bitmap? {
        // BitmapFactory options to downsize the image
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        o.inSampleSize = 6

        FileInputStream(file).use { input ->
            BitmapFactory.decodeStream(input, null, o)
        }

        // The new size we want to scale to
        val requiredSize = 75

        // Find the correct scale value. It should be the power of 2.
        var scale = 1
        while (o.outWidth / scale / 2 >= requiredSize && o.outHeight / scale / 2 >= requiredSize) {
            scale *= 2
        }

        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale

        val selectedBitmap = FileInputStream(file).use { input ->
            BitmapFactory.decodeStream(input, null, o2)
        }

        // Overriding the original image file
        file.createNewFile()
        FileOutputStream(file).use { output ->
            if (selectedBitmap == null) {
                return null
            }

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            return selectedBitmap
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
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