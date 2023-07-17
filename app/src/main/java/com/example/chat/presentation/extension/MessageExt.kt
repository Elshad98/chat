package com.example.chat.presentation.extension

import android.content.Context
import android.text.format.DateFormat
import com.example.chat.R
import com.example.chat.core.extension.isToday
import com.example.chat.core.extension.isYesterday
import com.example.chat.domain.message.Message
import java.text.SimpleDateFormat
import java.util.Locale

private val timeFormatter = SimpleDateFormat("HH:mm")
private val dateFormatterFullDate = SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yy MM dd"))

fun Message.getDateText(context: Context): String {
    return when {
        date.isToday -> timeFormatter.format(date)
        date.isYesterday -> context.getString(R.string.yesterday)
        else -> dateFormatterFullDate.format(date)
    }
}