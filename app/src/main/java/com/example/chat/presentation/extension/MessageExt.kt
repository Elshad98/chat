package com.example.chat.presentation.extension

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.format.DateFormat
import com.example.chat.R
import com.example.chat.core.extension.bold
import com.example.chat.core.extension.isToday
import com.example.chat.core.extension.isYesterday
import com.example.chat.domain.message.Message
import com.example.chat.domain.message.MessageType
import java.text.SimpleDateFormat
import java.util.Locale

private val timeFormatter = SimpleDateFormat("HH:mm")
private val dateFormatterFullDate = SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yy MM dd"))

fun Message.getDateText(context: Context): String {
    return when {
        createdAt.isToday() -> timeFormatter.format(createdAt)
        createdAt.isYesterday() -> context.getString(R.string.yesterday)
        else -> dateFormatterFullDate.format(createdAt)
    }
}

fun Message.messagePreview(context: Context): CharSequence {
    val previewText = when (type) {
        MessageType.TEXT -> message
        MessageType.IMAGE -> context.getString(R.string.chat_list_photo)
    }
    val sender = if (fromMe) {
        context.getString(R.string.chat_list_you)
    } else {
        null
    }
    return listOf(sender, previewText.bold())
        .filterNot { it.isNullOrEmpty() }
        .joinTo(SpannableStringBuilder(), ": ")
}