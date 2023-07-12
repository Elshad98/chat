package com.example.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chat.data.local.model.FriendEntity

@Dao
interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFriend(friend: FriendEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFriends(friends: List<FriendEntity>)

    @Query("SELECT * from friends WHERE id = :id")
    fun getFriend(id: Long): FriendEntity?

    @Query("SELECT * from friends WHERE is_request = 0")
    fun getFriends(): List<FriendEntity>

    @Query("SELECT * from friends WHERE is_request = 1")
    fun getFriendRequests(): List<FriendEntity>

    @Query("DELETE FROM friends WHERE id = :id")
    fun deleteFriend(id: Long)
}