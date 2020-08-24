package me.shkschneider.skeleton.javax

import java.util.Calendar
import java.util.TimeZone

// <https://github.com/nowfalsalahudeen/KdroidExt>
class MutableDateTime : DateTime, Comparable<DateTime> {

    constructor(timeInMillis: Long = System.currentTimeMillis()) : super(timeInMillis)

    constructor(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0,
        millis: Int = 0
    ) : super(year, month, day, hour, minute, second, millis)

    override var year: Int
        get() = super.year
        set(value) = calendar.set(Calendar.YEAR, value)

    override var month: Int
        get() = super.month
        set(value) = calendar.set(Calendar.MONTH, value - 1)

    override var day: Int
        get() = super.day
        set(value) = calendar.set(Calendar.DAY_OF_MONTH, value)

    override var dayOfWeek: Int
        get() = super.dayOfWeek
        set(value) = calendar.set(Calendar.DAY_OF_WEEK, value)

    override var hour: Int
        get() = super.hour
        set(value) = calendar.set(Calendar.HOUR_OF_DAY, value)

    override var minute: Int
        get() = super.minute
        set(value) = calendar.set(Calendar.MINUTE, value)

    override var second: Int
        get() = super.second
        set(value) = calendar.set(Calendar.SECOND, value)

    override var millis: Long
        get() = super.millis
        set(value) = calendar.set(Calendar.MILLISECOND, value.toInt())

    override var timeZone: TimeZone
        get() = super.timeZone
        set(value) {
            calendar.timeZone = value
        }

    fun truncate() {
        hour = 0
        minute = 0
        second = 0
        millis = 0
    }

}
