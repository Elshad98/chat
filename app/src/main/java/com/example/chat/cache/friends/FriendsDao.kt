package com.example.chat.cache.friends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.chat.data.friends.FriendsCache
import com.example.chat.domain.friends.FriendEntity

@Dao
interface FriendsDao : FriendsCache {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friend: FriendEntity): Long

    @Update
    fun update(friend: FriendEntity)

    @Transaction
    override fun saveFriend(friend: FriendEntity) {
        if (insert(friend) == -1L) {
            update(friend)
        }
    }

    @Query("SELECT * from friends_table WHERE id = :id")
    override fun getFriend(id: Long): FriendEntity?

    @Query("SELECT * from friends_table WHERE is_request = 0")
    override fun getFriends(): List<FriendEntity>

    @Query("SELECT * from friends_table WHERE is_request = 1")
    override fun getFriendRequests(): List<FriendEntity>

    @Query("DELETE FROM friends_table WHERE id = :id")
    override fun deleteFriend(id: Long)
}