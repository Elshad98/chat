package com.example.chat.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.chat.data.repository.message.MessageCache
import com.example.chat.domain.message.Message

@Dao
interface MessageDao : MessageCache {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: Message): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: List<Message>): List<Long>

    @Update
    fun update(message: Message)

    @Transaction
    override fun saveMessage(message: Message) {
        if (insert(message) == -1L) {
            update(message)
        }
    }

    @Transaction
    override fun saveMessages(messages: List<Message>) {
        insert(messages)
    }

    @Query("SELECT * from messages_table ORDER BY message_date DESC")
    override fun getChats(): List<Message>

    @Query("SELECT * from messages_table WHERE (deleted_by_receiver_id = 0 AND deleted_by_sender_id = 0) AND (sender_id = :contactId OR receiver_id = :contactId) ORDER BY message_date ASC")
    override fun getMessagesWithContact(contactId: Long): List<Message>

    @Query("SELECT * from messages_table ORDER BY message_date DESC")
    fun getLiveChats(): LiveData<List<Message>>

    @Query("SELECT * from messages_table WHERE (deleted_by_receiver_id = 0 AND deleted_by_sender_id = 0) AND (sender_id = :contactId OR receiver_id = :contactId) ORDER BY message_date ASC")
    fun getLiveMessagesWithContact(contactId: Long): LiveData<List<Message>>

    @Query("DELETE from messages_table WHERE message_id = :messageId")
    override fun deleteMessagesByUser(messageId: Long)
}