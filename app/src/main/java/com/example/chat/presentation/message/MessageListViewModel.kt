package com.example.chat.presentation.message

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.media.CreateImageFile
import com.example.chat.domain.media.EncodeImageBitmap
import com.example.chat.domain.media.GetPickedImage
import com.example.chat.domain.message.DeleteMessage
import com.example.chat.domain.message.GetLiveMessagesWithContact
import com.example.chat.domain.message.GetMessagesWithContact
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.SendMessage
import toothpick.InjectConstructor

@InjectConstructor
class MessageListViewModel(
    private val sendMessage: SendMessage,
    private val deleteMessage: DeleteMessage,
    private val getPickedImage: GetPickedImage,
    private val createImageFile: CreateImageFile,
    private val encodeImageBitmap: EncodeImageBitmap,
    private val getLiveMessages: GetLiveMessagesWithContact,
    private val getMessagesWithContact: GetMessagesWithContact
) : BaseViewModel() {

    private val _cameraFile = MutableLiveData<Uri>()
    val cameraFile: LiveData<Uri> = _cameraFile

    fun sendMessage(receiverId: Long, message: String) {
        if (message.isEmpty()) {
            return
        }
        sendMessage(receiverId, message, "")
    }

    fun createCameraFile() {
        createImageFile(None(), viewModelScope) { either ->
            either.fold(::handleFailure, _cameraFile::setValue)
        }
    }

    fun getMessages(contactId: Long): LiveData<List<Message>> {
        return getLiveMessages(contactId)
    }

    fun deleteMessage(messageId: Long, contactId: Long) {
        deleteMessage(DeleteMessage.Params(messageId), viewModelScope) { either ->
            either.fold(::handleFailure) { getMessagesWithContact(contactId) }
        }
    }

    fun sendMessage(receiverId: Long, uri: Uri?) {
        getPickedImage(GetPickedImage.Params(uri), viewModelScope) { either ->
            either.fold(::handleFailure) { bitmap ->
                encodeImageBitmap(EncodeImageBitmap.Params(bitmap), viewModelScope) { either ->
                    either.fold(::handleFailure) { image ->
                        sendMessage(receiverId, "", image)
                    }
                }
            }
        }
    }

    fun sendImage(receiverId: Long) {
        getPickedImage(GetPickedImage.Params(_cameraFile.value), viewModelScope) { either ->
            either.fold(::handleFailure) { bitmap ->
                encodeImageBitmap(EncodeImageBitmap.Params(bitmap), viewModelScope) { either ->
                    either.fold(::handleFailure) { image ->
                        sendMessage(receiverId, "", image)
                    }
                }
            }
        }
    }

    fun getMessagesWithContact(contactId: Long) {
        getMessagesWithContact(GetMessagesWithContact.Params(contactId, true), viewModelScope) { either ->
            either.fold(::handleFailure) { }
        }
    }

    private fun sendMessage(receiverId: Long, message: String, image: String) {
        sendMessage(SendMessage.Params(receiverId, message, image), viewModelScope) { either ->
            either.fold(::handleFailure) {
                getMessagesWithContact(receiverId)
            }
        }
    }
}