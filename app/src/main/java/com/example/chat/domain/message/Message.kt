package com.example.chat.domain.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "messages_table")
@TypeConverters(ContactConverter::class)
data class Message(
    @PrimaryKey
    @SerializedName("message_id")
    @ColumnInfo(name = "message_id")
    var id: Long,
    @SerializedName("sender_id")
    @ColumnInfo(name = "sender_id")
    var senderId: Long,
    @SerializedName("receiver_id")
    @ColumnInfo(name = "receiver_id")
    var receiverId: Long,
    @SerializedName("message_date")
    @ColumnInfo(name = "message_date")
    var date: Long,
    @SerializedName("message_type_id")
    @ColumnInfo(name = "message_type_id")
    var type: Int,
    @SerializedName("deleted_by_sender_id")
    @ColumnInfo(name = "deleted_by_sender_id")
    var deletedBySender: Int = 0,
    @SerializedName("deleted_by_receiver_id")
    @ColumnInfo(name = "deleted_by_receiver_id")
    var deletedByReceiver: Int = 0,
    var message: String,
    var contact: Contact? = null,
    var fromMe: Boolean = false
) {

    constructor() : this(
        id = 0L,
        senderId = 0L,
        receiverId = 0L,
        date = 0L,
        type = 0,
        deletedBySender = 0,
        deletedByReceiver = 0,
        message = "",
        contact = null,
        fromMe = false
    )
}