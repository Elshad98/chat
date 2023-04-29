package com.example.chat.presentation.settings

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
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
    private val encodeImageBitmap: EncodeImageBitmap
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _updateProfileSuccess = MutableLiveData<Unit>()
    val updateProfileSuccess: LiveData<Unit>
        get() = _updateProfileSuccess

    override fun onCleared() {
        getUser.unsubscribe()
        editUser.unsubscribe()
        getPickedImage.unsubscribe()
        encodeImageBitmap.unsubscribe()
    }

    init {
        getUser(None()) { either ->
            either.fold(::handleFailure, _user::setValue)
        }
    }

    fun onImagePicked(uri: Uri?) {
        getPickedImage(uri) { either ->
            either.fold(::handleFailure) { bitmap ->
                encodeImageBitmap(bitmap) { either ->
                    either.fold(::handleFailure, ::changeImage)
                }
            }
        }
    }

    private fun changeImage(image: String) {
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