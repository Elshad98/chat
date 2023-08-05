package com.example.chat.presentation.settings

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.media.CreateImageFile
import com.example.chat.domain.media.EncodeImageBitmap
import com.example.chat.domain.media.GetPickedImage
import com.example.chat.domain.user.EditUser
import com.example.chat.domain.user.GetUser
import com.example.chat.domain.user.User
import toothpick.InjectConstructor

@InjectConstructor
class SettingsViewModel(
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

    fun getUser() {
        getUser(None(), viewModelScope) { either ->
            either.fold(::handleFailure, _user::setValue)
        }
    }

    fun onImagePicked(uri: Uri?) {
        getPickedImage(GetPickedImage.Params(uri), viewModelScope) { either ->
            either.fold(::handleFailure, ::handleImageBitmap)
        }
    }

    fun createCameraFile() {
        createImageFile(None(), viewModelScope) { either ->
            either.fold(::handleFailure, _cameraFile::setValue)
        }
    }

    fun changeProfilePhoto() {
        getPickedImage(GetPickedImage.Params(_cameraFile.value), viewModelScope) { either ->
            either.fold(::handleFailure, ::handleImageBitmap)
        }
    }

    private fun handleImageBitmap(bitmap: Bitmap) {
        encodeImageBitmap(EncodeImageBitmap.Params(bitmap), viewModelScope) { either ->
            either.fold(::handleFailure, ::changeProfilePhoto)
        }
    }

    private fun changeProfilePhoto(image: String) {
        getUser(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { user ->
                editUser(EditUser.Params(user.copy(image = image)), viewModelScope) { either ->
                    either.fold(::handleFailure) {
                        _updateProfileSuccess.value = Unit
                    }
                }
            }
        }
    }
}