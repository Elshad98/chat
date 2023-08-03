package com.example.chat.presentation.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.DeleteFriend
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriends
import toothpick.InjectConstructor

@InjectConstructor
class FriendListViewModel(
    private val getFriends: GetFriends,
    private val deleteFriend: DeleteFriend
) : BaseViewModel() {

    private val _friendList = MutableLiveData<List<Friend>>()
    private val _removedFriend = MutableLiveData<Friend>()
    val friendList: LiveData<List<Friend>> = _friendList
    val removedFriend: LiveData<Friend> = _removedFriend

    override fun onCleared() {
        getFriends.unsubscribe()
        deleteFriend.unsubscribe()
    }

    fun getFriends() {
        getFriends(true) { either ->
            either.fold(::handleFailure, _friendList::setValue)
        }
    }

    fun deleteFriend(friend: Friend) {
        deleteFriend(friend) { either ->
            either.fold(::handleFailure) {
                _removedFriend.value = friend
                getFriends()
            }
        }
    }
}