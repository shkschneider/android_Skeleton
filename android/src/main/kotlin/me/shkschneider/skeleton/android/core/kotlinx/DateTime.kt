package me.shkschneider.skeleton.android.core.kotlinx

import com.google.gson.internal.bind.util.ISO8601Utils
import java.util.Calendar
import java.util.TimeZone

private const val TIME_ZONE = "Universal"

// <https://github.com/nowfalsalahudeen/KdroidExt>
open class DateTime(
        timeInMillis: Long = System.currentTimeMillis()
) : Comparable<DateTime> {

    protected open val calendar: Calendar = Calendar.getInstance().also {
        it.timeInMillis = timeInMillis
    }

    constructor(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0,
        millis: Int = 0
    ) : this(Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, second)
        set(Calendar.MILLISECOND, millis)
    }.timeInMillis)

    open val year: Int
        get() = calendar.get(Calendar.YEAR)

    open val month: Int
        get() = calendar.get(Calendar.MONTH) + 1

    open val day: Int
        get() = calendar.get(Calendar.DAY_OF_MONTH)

    open val dayOfWeek: Int
        get() = calendar.get(Calendar.DAY_OF_WEEK)

    open val hour: Int
        get() = calendar.get(Calendar.HOUR_OF_DAY)

    open val minute: Int
        get() = calendar.get(Calendar.MINUTE)

    open val second: Int
        get() = calendar.get(Calendar.SECOND)

    open val millis: Long
        get() = calendar.get(Calendar.MILLISECOND).toLong()

    open val timeZone: TimeZone
        get() = calendar.timeZone

    val timestamp: Int
        get() = (calendar.timeInMillis / 1000).toInt()

    fun toUTC(): DateTime = DateTime(toCalendar().apply {
        timeZone = TimeZone.getTimeZone(TIME_ZONE)
    }.timeInMillis)

    fun toCalendar(): Calendar =
        (calendar.clone() as Calendar)

    fun toMutableDateTime(): MutableDateTime =
        MutableDateTime(calendar.timeInMillis)

    override fun equals(other: Any?): Boolean = when (other) {
        is DateTime -> calendar == other.calendar
        is Calendar -> calendar == other
        is Long -> calendar.timeInMillis == other
        else -> false
    }

    infix fun on(other: DateTime): Boolean =
        (calendar.compareTo(other.calendar) == 0)

    infix fun before(other: DateTime): Boolean =
        calendar < other.calendar

    infix fun onOrBefore(other: DateTime): Boolean =
        calendar <= other.calendar

    infix fun after(other: DateTime): Boolean =
        calendar > other.calendar

    infix fun onOrAfter(other: DateTime): Boolean =
        calendar >= other.calendar

    override fun compareTo(other: DateTime): Int =
        calendar.compareTo(other.calendar)

    override fun hashCode(): Int =
        calendar.hashCode()

    override fun toString(): String =
        ISO8601Utils.format(calendar.time)

    companion object {

        const val JANUARY: Int = Calendar.JANUARY + 1
        const val FEBRUARY: Int = Calendar.FEBRUARY + 1
        const val MARCH: Int = Calendar.MARCH + 1
        const val APRIL: Int = Calendar.APRIL + 1
        const val MAY: Int = Calendar.MAY + 1
        const val JUNE: Int = Calendar.JUNE + 1
        const val JULY: Int = Calendar.JULY + 1
        const val AUGUST: Int = Calendar.AUGUST + 1
        const val SEPTEMBER: Int = Calendar.SEPTEMBER + 1
        const val OCTOBER: Int = Calendar.OCTOBER + 1
        const val NOVEMBER: Int = Calendar.NOVEMBER + 1
        const val DECEMBER: Int = Calendar.DECEMBER + 1

        const val SUNDAY: Int = Calendar.SUNDAY
        const val MONDAY: Int = Calendar.MONDAY
        const val TUESDAY: Int = Calendar.TUESDAY
        const val WEDNESDAY: Int = Calendar.WEDNESDAY
        const val THURSDAY: Int = Calendar.THURSDAY
        const val FRIDAY: Int = Calendar.FRIDAY
        const val SATURDAY: Int = Calendar.SATURDAY

        const val AM: Int = Calendar.AM
        const val PM: Int = Calendar.PM

        @get:JvmName("epoch")
        @JvmStatic
        val epoch: DateTime
            get() = DateTime(0.toLong())

        @get:JvmName("now")
        @JvmStatic
        val now: DateTime
            get() = DateTime(System.currentTimeMillis())

        @get:JvmName("timestamp")
        @JvmStatic
        val timestamp: Int
            get() = now.timestamp

    }

}
