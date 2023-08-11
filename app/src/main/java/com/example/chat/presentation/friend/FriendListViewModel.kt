package com.example.chat.presentation.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.None
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriends
import com.example.chat.domain.friend.GetLiveFriends
import toothpick.InjectConstructor

@InjectConstructor
class FriendListViewModel(
    getLiveFriends: GetLiveFriends,
    private val getFriends: GetFriends
) : BaseViewModel() {

    private val _removedFriend = MutableLiveData<Friend>()
    val removedFriend: LiveData<Friend> = _removedFriend
    val friendList = getLiveFriends()

    fun getFriends() {
        getFriends(None(), viewModelScope) { either ->
            either.fold(::handleFailure) { }
        }
    }
}