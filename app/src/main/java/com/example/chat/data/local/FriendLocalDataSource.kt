package com.example.chat.data.local

import com.example.chat.data.local.dao.FriendDao
import com.example.chat.data.local.model.FriendEntity
import toothpick.InjectConstructor

@InjectConstructor
class FriendLocalDataSource(
    private val friendDao: FriendDao
) {

    fun deleteFriend(id: Long) {
        friendDao.deleteFriend(id)
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
}