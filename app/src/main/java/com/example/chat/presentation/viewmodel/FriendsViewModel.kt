package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chat.domain.friends.AddFriend
import com.example.chat.domain.friends.ApproveFriendRequest
import com.example.chat.domain.friends.CancelFriendRequest
import com.example.chat.domain.friends.DeleteFriend
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.domain.friends.GetFriendRequests
import com.example.chat.domain.friends.GetFriends
import com.example.chat.domain.type.None
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val getFriendsUseCase: GetFriends,
    private val deleteFriendUseCase: DeleteFriend,
    private val addFriendUseCase: AddFriend,
    private val getFriendRequestsUseCase: GetFriendRequests,
    private val approveFriendRequestUseCase: ApproveFriendRequest,
    private val cancelFriendRequestUseCase: CancelFriendRequest
) : BaseViewModel() {

    val friendsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    val friendRequestsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    val deleteFriendData: MutableLiveData<None> = MutableLiveData()
    val addFriendData: MutableLiveData<None> = MutableLiveData()
    val approveFriendData: MutableLiveData<None> = MutableLiveData()
    val cancelFriendData: MutableLiveData<None> = MutableLiveData()

    fun getFriends() {
        getFriendsUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleFriends)
        }
    }

    fun getFriendRequests() {
        getFriendRequestsUseCase(None()) { either ->
            either.fold(::handleFailure, ::handleFriendRequests)
        }
    }

    fun deleteFriend(friendEntity: FriendEntity) {
        deleteFriendUseCase(friendEntity) { either ->
            either.fold(::handleFailure, ::handleDeleteFriend)
        }
    }

    fun addFriend(email: String) {
        addFriendUseCase(AddFriend.Params(email)) { either ->
            either.fold(::handleFailure, ::handleAddFriend)
        }
    }

    fun approveFriend(friendEntity: FriendEntity) {
        approveFriendRequestUseCase(friendEntity) { either ->
            either.fold(::handleFailure, ::handleApproveFriend)
        }
    }

    fun cancelFriend(friendEntity: FriendEntity) {
        cancelFriendRequestUseCase(friendEntity) { either ->
            either.fold(::handleFailure, ::handleCancelFriend)
        }
    }

    private fun handleFriends(friends: List<FriendEntity>) {
        friendsData.value = friends
    }

    private fun handleFriendRequests(friends: List<FriendEntity>) {
        friendRequestsData.value = friends
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

    override fun onCleared() {
        super.onCleared()
        getFriendsUseCase.unsubscribe()
        getFriendRequestsUseCase.unsubscribe()
        deleteFriendUseCase.unsubscribe()
        addFriendUseCase.unsubscribe()
        approveFriendRequestUseCase.unsubscribe()
        cancelFriendRequestUseCase.unsubscribe()
    }
}