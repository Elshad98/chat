package com.example.chat.ui.core

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    @JvmStatic
    @BindingAdapter("date")
    fun TextView.setDate(date: Long) {
        text = date.parseDate()
    }
}

fun Long.parseDate(): String {
    val currentLocale = Locale.getDefault()

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    var sdf = SimpleDateFormat("dd.MM.yy", currentLocale)

    if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) &&
        calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
    ) {
        sdf = SimpleDateFormat("H:mm", currentLocale)
    } else if (
        calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1 &&
        calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
    ) {
        sdf = SimpleDateFormat("Вчера в H:mm", currentLocale)
    } else if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
        sdf = SimpleDateFormat("d MMM", currentLocale)
    }

    return sdf.format(Date(this))
}