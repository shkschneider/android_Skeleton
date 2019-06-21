package me.shkschneider.skeleton.extensions

import java.util.Calendar
import java.util.Locale

fun calendarOf(year: Int, month: Int, dayOfMonth: Int): Calendar = Calendar.getInstance(Locale.getDefault()).apply {
    this.year = year
    this.month = month
    this.dayOfMonth = dayOfMonth
}

fun Calendar.midnight() {
    set(Calendar.SECOND, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.HOUR_OF_DAY, 0)
}

inline var Calendar.year
    get() = get(Calendar.YEAR)
    set(value) = set(Calendar.YEAR, value)

inline var Calendar.month
    get() = get(Calendar.MONTH)
    set(value) = set(Calendar.MONTH, value)

inline var Calendar.weekOfYear
    get() = get(Calendar.WEEK_OF_YEAR)
    set(value) = set(Calendar.WEEK_OF_YEAR, value)

inline var Calendar.weekOfMonth
    get() = get(Calendar.WEEK_OF_MONTH)
    set(value) = set(Calendar.WEEK_OF_MONTH, value)

inline var Calendar.dayOfMonth
    get() = get(Calendar.DAY_OF_MONTH)
    set(value) = set(Calendar.DAY_OF_MONTH, value)

inline var Calendar.dayOfYear
    get() = get(Calendar.DAY_OF_YEAR)
    set(value) = set(Calendar.DAY_OF_YEAR, value)

inline var Calendar.dayOfWeek
    get() = get(Calendar.DAY_OF_WEEK)
    set(value) = set(Calendar.DAY_OF_WEEK, value)

inline var Calendar.dayOfWeekInMonth
    get() = get(Calendar.DAY_OF_WEEK_IN_MONTH)
    set(value) = set(Calendar.DAY_OF_WEEK_IN_MONTH, value)

inline var Calendar.amPm
    get() = get(Calendar.AM_PM)
    set(value) = set(Calendar.AM_PM, value)

inline var Calendar.hour
    get() = get(Calendar.HOUR)
    set(value) = set(Calendar.HOUR, value)

inline var Calendar.hourOfDay
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) = set(Calendar.HOUR_OF_DAY, value)

inline var Calendar.minute
    get() = get(Calendar.MINUTE)
    set(value) = set(Calendar.MINUTE, value)

inline var Calendar.second
    get() = get(Calendar.SECOND)
    set(value) = set(Calendar.SECOND, value)

inline var Calendar.millisecond
    get() = get(Calendar.MILLISECOND)
    set(value) = set(Calendar.MILLISECOND, value)
