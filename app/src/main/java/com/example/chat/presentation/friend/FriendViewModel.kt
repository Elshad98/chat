package com.example.chat.presentation.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.DeleteFriend
import com.example.chat.domain.friend.Friend
import toothpick.InjectConstructor

@InjectConstructor
class FriendViewModel(
    private val deleteFriend: DeleteFriend
) : BaseViewModel() {

    private val _deleteSuccess = MutableLiveData<Unit>()
    val deleteSuccess: LiveData<Unit> = _deleteSuccess

    fun deleteFriend(friend: Friend) {
        deleteFriend(DeleteFriend.Params(friend), viewModelScope) { either ->
            either.fold(::handleFailure) { _deleteSuccess.value = Unit }
        }
    }
}