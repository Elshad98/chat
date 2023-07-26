package com.example.chat.presentation.settings

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.media.CreateImageFile
import com.example.chat.domain.media.EncodeImageBitmap
import com.example.chat.domain.media.GetPickedImage
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.User
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getUser: GetUser,
    private val editUser: EditUser,
    private val getPickedImage: GetPickedImage,
    private val createImageFile: CreateImageFile,
    private val encodeImageBitmap: EncodeImageBitmap
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    private val _updateProfileSuccess = MutableLiveData<Unit>()
    private val _cameraFile = MutableLiveData<Uri>()
    val user: LiveData<User> = _user
    val updateProfileSuccess: LiveData<Unit> = _updateProfileSuccess
    val cameraFile: LiveData<Uri> = _cameraFile

    override fun onCleared() {
        getUser.unsubscribe()
        editUser.unsubscribe()
        getPickedImage.unsubscribe()
        createImageFile.unsubscribe()
        encodeImageBitmap.unsubscribe()
    }

    fun getUser() {
        getUser(None()) { either ->
            either.fold(::handleFailure, _user::setValue)
        }
    }

    fun onImagePicked(uri: Uri?) {
        getPickedImage(uri) { either ->
            either.fold(::handleFailure, ::handleImageBitmap)
        }
    }

    fun createCameraFile() {
        createImageFile(None()) { either ->
            either.fold(::handleFailure, _cameraFile::setValue)
        }
    }

    fun changeProfilePhoto() {
        getPickedImage(_cameraFile.value) { either ->
            either.fold(::handleFailure, ::handleImageBitmap)
        }
    }

    private fun handleImageBitmap(bitmap: Bitmap) {
        encodeImageBitmap(bitmap) { either ->
            either.fold(::handleFailure, ::changeProfilePhoto)
        }
    }

    private fun changeProfilePhoto(image: String) {
        getUser(None()) { either ->
            either.fold(::handleFailure) { user ->
                editUser(user.copy(image = image)) { either ->
                    either.fold(::handleFailure) {
                        _updateProfileSuccess.value = Unit
                    }
                }
            }
        }
    }
}