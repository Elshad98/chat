package com.example.chat.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.chat.data.local.model.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: MessageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: List<MessageEntity>): List<Long>

    @Update
    fun update(message: MessageEntity)

    @Transaction
    fun saveMessage(message: MessageEntity) {
        if (insert(message) == -1L) {
            update(message)
        }
    }

    @Transaction
    fun saveMessages(messages: List<MessageEntity>) {
        insert(messages)
    }

    @Query("SELECT * from messages ORDER BY message_date DESC")
    fun getChats(): List<MessageEntity>

    @Query("SELECT * from messages WHERE (deleted_by_receiver_id = 0 AND deleted_by_sender_id = 0) AND (sender_id = :contactId OR receiver_id = :contactId) ORDER BY message_date ASC")
    fun getMessagesWithContact(contactId: Long): List<MessageEntity>

    @Query("SELECT * from messages ORDER BY message_date DESC")
    fun getLiveChats(): LiveData<List<MessageEntity>>

    @Query("SELECT * from messages WHERE (deleted_by_receiver_id = 0 AND deleted_by_sender_id = 0) AND (sender_id = :contactId OR receiver_id = :contactId) ORDER BY message_date ASC")
    fun getLiveMessagesWithContact(contactId: Long): LiveData<List<MessageEntity>>

    @Query("DELETE from messages WHERE message_id = :messageId")
    fun deleteMessageByUser(messageId: Long)
}