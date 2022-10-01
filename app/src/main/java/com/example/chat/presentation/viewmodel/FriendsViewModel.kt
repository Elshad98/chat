package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.friend.AddFriend
import com.example.chat.domain.friend.ApproveFriendRequest
import com.example.chat.domain.friend.CancelFriendRequest
import com.example.chat.domain.friend.DeleteFriend
import com.example.chat.domain.friend.Friend
import com.example.chat.domain.friend.GetFriendRequests
import com.example.chat.domain.friend.GetFriends
import com.example.chat.domain.type.None
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val addFriendUseCase: AddFriend,
    private val getFriendsUseCase: GetFriends,
    private val deleteFriendUseCase: DeleteFriend,
    private val getFriendRequestsUseCase: GetFriendRequests,
    private val cancelFriendRequestUseCase: CancelFriendRequest,
    private val approveFriendRequestUseCase: ApproveFriendRequest
) : BaseViewModel() {

    val addFriendData: MutableLiveData<None> = MutableLiveData()
    val deleteFriendData: MutableLiveData<None> = MutableLiveData()
    val cancelFriendData: MutableLiveData<None> = MutableLiveData()
    val approveFriendData: MutableLiveData<None> = MutableLiveData()
    val friendsData: MutableLiveData<List<Friend>> = MutableLiveData()
    val friendRequestsData: MutableLiveData<List<Friend>> = MutableLiveData()

    fun getFriends(needFetch: Boolean = false) {
        getFriendsUseCase(needFetch) { either ->
            either.fold(::handleFailure) { handleFriends(it, !needFetch) }
        }
    }

    fun getFriendRequests(needFetch: Boolean = false) {
        getFriendRequestsUseCase(needFetch) { either ->
            either.fold(::handleFailure) { handleFriendRequests(it, !needFetch) }
        }
    }

    fun deleteFriend(friend: Friend) {
        deleteFriendUseCase(friend) { either ->
            either.fold(::handleFailure, ::handleDeleteFriend)
        }
    }

    fun addFriend(email: String) {
        addFriendUseCase(AddFriend.Params(email)) { either ->
            either.fold(::handleFailure, ::handleAddFriend)
        }
    }

    fun approveFriend(friend: Friend) {
        approveFriendRequestUseCase(friend) { either ->
            either.fold(::handleFailure, ::handleApproveFriend)
        }
    }

    fun cancelFriend(friend: Friend) {
        cancelFriendRequestUseCase(friend) { either ->
            either.fold(::handleFailure, ::handleCancelFriend)
        }
    }

    override fun onCleared() {
        super.onCleared()
        addFriendUseCase.unsubscribe()
        getFriendsUseCase.unsubscribe()
        deleteFriendUseCase.unsubscribe()
        getFriendRequestsUseCase.unsubscribe()
        cancelFriendRequestUseCase.unsubscribe()
        approveFriendRequestUseCase.unsubscribe()
    }

    private fun handleFriends(friends: List<Friend>, fromCache: Boolean) {
        friendsData.value = friends
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getFriends(true)
        }
    }

    private fun handleFriendRequests(friends: List<Friend>, fromCache: Boolean) {
        friendRequestsData.value = friends
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getFriendRequests(true)
        }
    }

    private fun handleDeleteFriend(none: None?) {
        deleteFriendData.value = none
    }

    private fun handleAddFriend(none: None?) {
        addFriendData.value = none
    }

    private fun handleApproveFriend(none: None?) {
        approveFriendData.value = none
    }

    private fun handleCancelFriend(none: None?) {
        cancelFriendData.value = none
    }
}