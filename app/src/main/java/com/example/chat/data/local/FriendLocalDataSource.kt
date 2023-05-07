package com.example.chat.data.local

import com.example.chat.data.local.dao.FriendDao
import com.example.chat.data.local.model.FriendEntity
import javax.inject.Inject

class FriendLocalDataSource @Inject constructor(
    private val friendDao: FriendDao
) {

    fun removeFriend(id: Long) {
        friendDao.removeFriend(id)
    }

    fun saveFriend(friend: FriendEntity) {
        friendDao.saveFriend(friend)
    }

    fun saveFriends(friends: List<FriendEntity>) {
        friendDao.saveFriends(friends)
    }

    fun getFriends(): List<FriendEntity> {
        return friendDao.getFriends()
    }

    fun getFriend(id: Long): FriendEntity? {
        return friendDao.getFriend(id)
    }

    fun getFriendRequests(): List<FriendEntity> {
        return friendDao.getFriendRequests()
    }
}