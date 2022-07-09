package com.example.chat.presentation.viewmodel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.media.CreateImageFile
import com.example.chat.domain.media.EncodeImageBitmap
import com.example.chat.domain.media.GetPickedImage
import com.example.chat.domain.type.None
import javax.inject.Inject

class MediaViewModel @Inject constructor(
    private val getPickedImageUseCase: GetPickedImage,
    private val createImageFileUseCase: CreateImageFile,
    private val encodeImageBitmapUseCase: EncodeImageBitmap
) : BaseViewModel() {

    companion object {

        const val PICK_IMAGE_REQUEST_CODE = 10001
        const val CAPTURE_IMAGE_REQUEST_CODE = 10002
    }

    val cameraFileCreatedData: MutableLiveData<Uri> = MutableLiveData()
    val pickedImageData: MutableLiveData<PickedImage> = MutableLiveData()
    private val imageBitmapData: MutableLiveData<Bitmap> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        getPickedImageUseCase.unsubscribe()
        createImageFileUseCase.unsubscribe()
        encodeImageBitmapUseCase.unsubscribe()
    }

    fun createCameraFile() {
        createImageFileUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleCameraFileCreated)
        }
    }

    fun onPickImageResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        Log.d("MediaViewModel", "requestCode: $requestCode | resultCode: $resultCode | data: ${intent?.data}")
        if (resultCode == Activity.RESULT_OK) {
            val uri = when (requestCode) {
                PICK_IMAGE_REQUEST_CODE -> intent?.data
                CAPTURE_IMAGE_REQUEST_CODE -> cameraFileCreatedData.value
                else -> null
            }

            getPickedImage(uri)
        }
    }

    private fun handleCameraFileCreated(uri: Uri) {
        cameraFileCreatedData.value = uri
    }

    private fun handleImageBitmap(bitmap: Bitmap) {
        imageBitmapData.value = bitmap
        encodeImage(bitmap)
    }

    private fun handleImageString(string: String) {
        pickedImageData.value = PickedImage(imageBitmapData.value!!, string)
        updateProgress(false)
    }

    private fun getPickedImage(uri: Uri?) {
        updateProgress(true)
        getPickedImageUseCase(uri) { either ->
            either.fold(::handleFailure, ::handleImageBitmap)
        }
    }

    private fun encodeImage(bitmap: Bitmap) {
        encodeImageBitmapUseCase(bitmap) { either ->
            either.fold(::handleFailure, ::handleImageString)
        }
    }

    class PickedImage(val bitmap: Bitmap, val string: String)
}