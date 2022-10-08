package com.example.chat.data.remote.model.response

import com.example.chat.data.remote.model.dto.FriendDto

class FriendsResponse(
    success: Int,
    message: String,
    val friends: List<FriendDto>
) : BaseResponse(success, message)