package com.example.chat.remote.friends

import com.example.chat.domain.friends.FriendEntity
import com.example.chat.remote.core.BaseResponse
import com.google.gson.annotations.SerializedName

class GetFriendRequestsResponse(
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<FriendEntity>
) : BaseResponse(success, message)