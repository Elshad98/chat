package com.example.chat.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chat.data.local.model.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessages(messages: List<MessageEntity>)

    @Query("SELECT * from messages ORDER BY createdAt DESC")
    fun getLiveChats(): LiveData<List<MessageEntity>>

    @Query("DELETE from messages WHERE id = :messageId")
    fun deleteMessageByUser(messageId: Long)

    @Query(
        "SELECT * from messages " +
            "WHERE (deleted_by_receiver_id = 0 AND deleted_by_sender_id = 0) " +
            "AND (sender_id = :contactId OR receiver_id = :contactId) " +
            "ORDER BY createdAt ASC"
    )
    fun getLiveMessagesWithContact(contactId: Long): LiveData<List<MessageEntity>>
}