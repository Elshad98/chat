package com.example.chat.data.remote.friends

import com.example.chat.data.remote.core.BaseResponse
import com.example.chat.domain.friends.FriendEntity

class GetFriendsResponse(
    success: Int,
    message: String,
    val friends: List<FriendEntity>
) : BaseResponse(success, message)