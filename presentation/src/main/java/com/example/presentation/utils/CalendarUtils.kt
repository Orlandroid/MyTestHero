package com.example.presentation.utils

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


fun getToday(): Long {
    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = getCalendar()
    calendar.timeInMillis = today
    return calendar.timeInMillis
}

fun getCalendar(timeZone: String = "UTC"): Calendar {
    return Calendar.getInstance(TimeZone.getTimeZone(timeZone))
}

fun getConstraints(): CalendarConstraints {
    return CalendarConstraints.Builder()
        .setOpenAt(getToday())
        .setStart(getToday())
        .build()
}
