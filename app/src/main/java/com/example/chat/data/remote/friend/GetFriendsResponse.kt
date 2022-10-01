package com.example.chat.data.remote.friend

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.friend.Friend

class GetFriendsResponse(
    success: Int,
    message: String,
    val friends: List<Friend>
) : BaseResponse(success, message)