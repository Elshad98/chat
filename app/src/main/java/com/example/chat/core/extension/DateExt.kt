package com.example.chat.core.extension

import java.util.Calendar
import java.util.Date

private const val ONE_MINUTE_IN_MILLIS = 60_000

fun Date.isToday(): Boolean = isDateIn(this, 0)

fun Date.isYesterday(): Boolean = isDateIn(this, -1)

fun Date.isInLastMinute(): Boolean = (Date().time - ONE_MINUTE_IN_MILLIS < time)

private fun isDateIn(date: Date, amount: Int = 0): Boolean {
    val now = Calendar.getInstance().apply { add(Calendar.DATE, amount) }
    val cdate = Calendar.getInstance().apply { time = date }

    return now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR) &&
        now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH) &&
        now.get(Calendar.DATE) == cdate.get(Calendar.DATE)
}