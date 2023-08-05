package com.example.chat.presentation.friendrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.ApproveFriendRequest
import com.example.chat.domain.friend.CancelFriendRequest
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriendRequests
import toothpick.InjectConstructor

@InjectConstructor
class FriendRequestListViewModel(
    private val getFriendRequests: GetFriendRequests,
    private val cancelFriendRequest: CancelFriendRequest,
    private val approveFriendRequest: ApproveFriendRequest
) : BaseViewModel() {

    private val _friendRequests = MutableLiveData<List<Friend>>()
    val friendRequests: LiveData<List<Friend>> = _friendRequests

    fun approveFriendRequest(friend: Friend) {
        approveFriendRequest(ApproveFriendRequest.Params(friend), viewModelScope) { either ->
            either.fold(::handleFailure) { getFriendRequests() }
        }
    }

    fun cancelFriendRequest(friend: Friend) {
        cancelFriendRequest(CancelFriendRequest.Params(friend), viewModelScope) { either ->
            either.fold(::handleFailure) { getFriendRequests() }
        }
    }

    fun getFriendRequests(needFetch: Boolean = false) {
        getFriendRequests(GetFriendRequests.Params(needFetch), viewModelScope) { either ->
            either.fold(::handleFailure, _friendRequests::setValue)
        }
    }
}