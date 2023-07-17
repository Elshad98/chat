package com.example.chat.core.extension

import java.util.Calendar
import java.util.Date

val Date.isToday: Boolean
    get() = isDateIn(this, 0)

val Date.isYesterday: Boolean
    get() = isDateIn(this, -1)

private fun isDateIn(date: Date, amount: Int = 0): Boolean {
    val now = Calendar.getInstance().apply { add(Calendar.DATE, amount) }
    val cdate = Calendar.getInstance().apply { time = date }

    return now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR) &&
        now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH) &&
        now.get(Calendar.DATE) == cdate.get(Calendar.DATE)
}