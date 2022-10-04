package com.example.chat.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.chat.data.repository.friend.FriendCache
import com.example.chat.domain.friend.Friend

@Dao
interface FriendDao : FriendCache {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friend: Friend): Long

    @Update
    fun update(friend: Friend)

    @Transaction
    override fun saveFriend(friend: Friend) {
        if (insert(friend) == -1L) {
            update(friend)
        }
    }

    @Query("SELECT * from friends_table WHERE id = :id")
    override fun getFriend(id: Long): Friend?

    @Query("SELECT * from friends_table WHERE is_request = 0")
    override fun getFriends(): List<Friend>

    @Query("SELECT * from friends_table WHERE is_request = 1")
    override fun getFriendRequests(): List<Friend>

    @Query("DELETE FROM friends_table WHERE id = :id")
    override fun deleteFriend(id: Long)
}