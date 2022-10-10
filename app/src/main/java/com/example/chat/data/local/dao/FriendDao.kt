package com.example.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.chat.data.local.model.FriendEntity

@Dao
interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friend: FriendEntity): Long

    @Update
    fun update(friend: FriendEntity)

    @Transaction
    fun saveFriend(friend: FriendEntity) {
        if (insert(friend) == -1L) {
            update(friend)
        }
    }

    @Query("SELECT * from friends WHERE id = :id")
    fun getFriend(id: Long): FriendEntity?

    @Query("SELECT * from friends WHERE is_request = 0")
    fun getFriends(): List<FriendEntity>

    @Query("SELECT * from friends WHERE is_request = 1")
    fun getFriendRequests(): List<FriendEntity>

    @Query("DELETE FROM friends WHERE id = :id")
    fun deleteFriend(id: Long)
}