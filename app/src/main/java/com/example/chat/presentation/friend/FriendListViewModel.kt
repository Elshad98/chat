package com.example.chat.presentation.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriends
import com.example.chat.domain.friend.RemoveFriend
import javax.inject.Inject

class FriendListViewModel @Inject constructor(
    private val getFriends: GetFriends,
    private val removeFriend: RemoveFriend
) : BaseViewModel() {

    private val _friendList = MutableLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>>
        get() = _friendList

    private val _removedFriend = MutableLiveData<Friend>()
    val removedFriend: LiveData<Friend>
        get() = _removedFriend

    override fun onCleared() {
        getFriends.unsubscribe()
        removeFriend.unsubscribe()
    }

    fun getFriends() {
        getFriends(true) { either ->
            either.fold(::handleFailure, _friendList::setValue)
        }
    }

    fun removeFriend(friend: Friend) {
        removeFriend(friend) { either ->
            either.fold(::handleFailure) {
                _removedFriend.value = friend
                getFriends()
            }
        }
    }
}