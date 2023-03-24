package com.example.chat.presentation.friendrequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.ApproveFriendRequest
import com.example.chat.domain.friend.CancelFriendRequest
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriendRequests
import javax.inject.Inject

class FriendRequestsViewModel @Inject constructor(
    private val getFriendRequests: GetFriendRequests,
    private val cancelFriendRequest: CancelFriendRequest,
    private val approveFriendRequest: ApproveFriendRequest
) : BaseViewModel() {

    private val _friendRequests = MutableLiveData<List<Friend>>()
    val friendRequests: LiveData<List<Friend>>
        get() = _friendRequests

    override fun onCleared() {
        getFriendRequests.unsubscribe()
        cancelFriendRequest.unsubscribe()
        approveFriendRequest.unsubscribe()
    }

    fun approveFriendRequest(friend: Friend) {
        approveFriendRequest(friend) { either ->
            either.fold(::handleFailure) { getFriendRequests() }
        }
    }

    fun cancelFriendRequest(friend: Friend) {
        cancelFriendRequest(friend) { either ->
            either.fold(::handleFailure) { getFriendRequests() }
        }
    }

    fun getFriendRequests(needFetch: Boolean = false) {
        getFriendRequests(needFetch) { either ->
            either.fold(::handleFailure, _friendRequests::setValue)
        }
    }
}