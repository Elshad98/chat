package com.example.chat.presentation.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.DeleteFriend
import com.example.chat.domain.friend.Friend
import javax.inject.Inject

class FriendViewModel @Inject constructor(
    private val deleteFriend: DeleteFriend
) : BaseViewModel() {

    private val _deleteSuccess = MutableLiveData<Unit>()
    val deleteSuccess: LiveData<Unit> = _deleteSuccess

    override fun onCleared() {
        super.onCleared()
        deleteFriend.unsubscribe()
    }

    fun deleteFriend(friend: Friend) {
        deleteFriend(friend) { either ->
            either.fold(::handleFailure) { _deleteSuccess.value = Unit }
        }
    }
}