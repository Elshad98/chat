package com.example.chat.presentation.invitation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.core.platform.BaseViewModel
import com.example.chat.domain.friend.ApproveFriendRequest
import com.example.chat.domain.friend.CancelFriendRequest
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriendRequests
import javax.inject.Inject

class InvitationListViewModel @Inject constructor(
    private val getFriendRequests: GetFriendRequests,
    private val cancelFriendRequest: CancelFriendRequest,
    private val approveFriendRequest: ApproveFriendRequest
) : BaseViewModel() {

    private val _friendRequests = MutableLiveData<List<Friend>>()
    val friendRequests: LiveData<List<Friend>>
        get() = _friendRequests

    fun approveFriend(friend: Friend) {
        approveFriendRequest(friend) { either ->
            either.fold(::handleFailure) { getFriendRequests() }
        }
    }

    fun cancelFriend(friend: Friend) {
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