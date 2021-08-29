package com.example.chat.remote.friends

import com.example.chat.domain.friends.FriendEntity
import com.example.chat.remote.core.BaseResponse

class GetFriendsResponse(
    success: Int,
    message: String,
    val friends: List<FriendEntity>
) : BaseResponse(success, message)