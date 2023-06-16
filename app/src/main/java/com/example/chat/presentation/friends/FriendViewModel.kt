package com.example.chat.presentation.friends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.RemoveFriend
import javax.inject.Inject

class FriendViewModel @Inject constructor(
    private val removeFriend: RemoveFriend
) : BaseViewModel() {

    private val _removeSuccess = MutableLiveData<Unit>()
    val removeSuccess: LiveData<Unit>
        get() = _removeSuccess

    override fun onCleared() {
        super.onCleared()
        removeFriend.unsubscribe()
    }

    fun removeFriend(friend: Friend) {
        removeFriend(friend) { either ->
            either.fold(::handleFailure) { _removeSuccess.value = Unit }
        }
    }
}