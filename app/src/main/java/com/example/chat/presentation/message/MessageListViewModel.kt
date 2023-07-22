package com.example.chat.presentation.message

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.media.CreateImageFile
import com.example.chat.domain.media.EncodeImageBitmap
import com.example.chat.domain.media.GetPickedImage
import com.example.chat.domain.message.GetLiveMessagesWithContact
import com.example.chat.domain.message.GetMessagesWithContact
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.SendMessage
import javax.inject.Inject

class MessageListViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    private val getPickedImage: GetPickedImage,
    private val createImageFile: CreateImageFile,
    private val encodeImageBitmap: EncodeImageBitmap,
    private val getLiveMessages: GetLiveMessagesWithContact,
    private val getMessagesWithContact: GetMessagesWithContact
) : BaseViewModel() {

    private val _cameraFile = MutableLiveData<Uri>()
    val cameraFile: LiveData<Uri>
        get() = _cameraFile

    override fun onCleared() {
        super.onCleared()
        sendMessage.unsubscribe()
        getPickedImage.unsubscribe()
        createImageFile.unsubscribe()
        encodeImageBitmap.unsubscribe()
        getMessagesWithContact.unsubscribe()
    }

    fun sendMessage(receiverId: Long, message: String) {
        if (message.isEmpty()) {
            return
        }
        sendMessage(receiverId, message, "")
    }

    fun createCameraFile() {
        createImageFile(None()) { either ->
            either.fold(::handleFailure, _cameraFile::setValue)
        }
    }

    fun getMessages(contactId: Long): LiveData<List<Message>> {
        return getLiveMessages(contactId)
    }

    fun sendMessage(receiverId: Long, uri: Uri?) {
        getPickedImage(uri) { either ->
            either.fold(::handleFailure) { bitmap ->
                encodeImageBitmap(bitmap) { either ->
                    either.fold(::handleFailure) { image ->
                        sendMessage(receiverId, "", image)
                    }
                }
            }
        }
    }

    fun sendImage(receiverId: Long) {
        getPickedImage(_cameraFile.value) { either ->
            either.fold(::handleFailure) { bitmap ->
                encodeImageBitmap(bitmap) { either ->
                    either.fold(::handleFailure) { image ->
                        sendMessage(receiverId, "", image)
                    }
                }
            }
        }
    }

    fun getMessagesWithContact(contactId: Long) {
        getMessagesWithContact(GetMessagesWithContact.Params(contactId, true)) { either ->
            either.fold(::handleFailure) { }
        }
    }

    private fun sendMessage(receiverId: Long, message: String, image: String) {
        sendMessage(SendMessage.Params(receiverId, message, image)) { either ->
            either.fold(::handleFailure) {
                getMessagesWithContact(receiverId)
            }
        }
    }
}