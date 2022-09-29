package com.example.chat.data.remote.friends

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.friends.FriendEntity
import com.google.gson.annotations.SerializedName

class GetFriendRequestsResponse(
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<FriendEntity>
) : BaseResponse(success, message)