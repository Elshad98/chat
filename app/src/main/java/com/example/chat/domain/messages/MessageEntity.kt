package com.example.chat.domain.messages

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "messages_table")
@TypeConverters(ContactConverter::class)
data class MessageEntity(
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
    var message: String,
    var contact: ContactEntity? = null,
    var fromMe: Boolean = false
) {

    constructor() : this(
        id = 0L,
        senderId = 0L,
        receiverId = 0L,
        date = 0L,
        type = 0,
        message = "",
        contact = null,
        fromMe = false
    )
}