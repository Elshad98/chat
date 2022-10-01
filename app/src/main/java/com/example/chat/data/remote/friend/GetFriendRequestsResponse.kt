package com.example.chat.data.remote.friend

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.friend.Friend
import com.google.gson.annotations.SerializedName

class GetFriendRequestsResponse(
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<Friend>
) : BaseResponse(success, message)