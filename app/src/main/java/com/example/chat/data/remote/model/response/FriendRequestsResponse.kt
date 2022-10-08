package com.example.chat.data.remote.model.response

import com.example.chat.data.remote.model.dto.FriendDto
import com.google.gson.annotations.SerializedName

class FriendRequestsResponse(
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<FriendDto>
) : BaseResponse(success, message)