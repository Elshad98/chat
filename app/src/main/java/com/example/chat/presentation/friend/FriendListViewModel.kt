package com.example.chat.presentation.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriends
import javax.inject.Inject

class FriendListViewModel @Inject constructor(
    private val getFriends: GetFriends
) : BaseViewModel() {

    private val _friendList = MutableLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>>
        get() = _friendList

    fun getFriends() {
        getFriends(true) { either ->
            either.fold(::handleFailure, _friendList::setValue)
        }
    }
}