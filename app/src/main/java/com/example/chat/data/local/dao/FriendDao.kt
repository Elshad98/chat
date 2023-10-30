package com.example.chat.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chat.data.local.model.FRIEND_ENTITY_TABLE_NAME
import com.example.chat.data.local.model.FriendEntity

@Dao
interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFriend(friend: FriendEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFriends(friends: List<FriendEntity>)

    @Query("DELETE FROM $FRIEND_ENTITY_TABLE_NAME WHERE id = :id")
    fun deleteFriend(id: Long)

    @Query("SELECT * from $FRIEND_ENTITY_TABLE_NAME WHERE is_request = 0 ORDER BY last_seen")
    fun getLiveFriends(): LiveData<List<FriendEntity>>
}