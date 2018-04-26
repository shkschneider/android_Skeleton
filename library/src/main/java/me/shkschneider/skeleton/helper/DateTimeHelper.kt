package me.shkschneider.skeleton.helper

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.annotation.IntRange
import android.text.format.DateFormat
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeHelper {

    // <http://developer.android.com/intl/ru/reference/java/text/SimpleDateFormat.html>
    val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    // <https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1>
    val HTTP_DATE = "EEE, dd MMM yyyy HH:mm:ss z"

    private var calendar: Calendar? = null

    @Synchronized
    fun calendar(): Calendar {
        calendar ?: run {
            calendar = Calendar.getInstance(LocaleHelper.Device.locale())
        }
        return calendar as Calendar
    }

    fun epoch(): Calendar {
        val calendar = calendar()
        calendar.timeInMillis = 0.toLong()
        return calendar
    }

    fun day(): Int {
        return calendar().get(Calendar.DAY_OF_MONTH)
    }

    fun month(): Int {
        return calendar().get(Calendar.MONTH) + 1
    }

    fun year(): Int {
        return calendar().get(Calendar.YEAR)
    }

    // <http://howtodoinjava.com/2012/12/16/always-use-setlenient-false-when-using-simpledateformat-for-date-validation-in-java/>
    fun format(calendar: Calendar, f: String?): String {
        val format = if (f?.isNotBlank() == true) f else ISO_8601
        calendar.isLenient = false
        return SimpleDateFormat(format, LocaleHelper.Device.locale()).format(calendar.time)
    }

    fun now(): Long {
        return System.currentTimeMillis()
    }

    fun timestamp(): Int {
        return TimeUnit.MILLISECONDS.toSeconds(now()).toInt()
    }

    fun gmtOffset(): Int {
        return Math.round((TimeZone.getDefault().getOffset(TimeUnit.MILLISECONDS.toSeconds(calendar().timeInMillis))).toFloat())
    }

    fun relative(@IntRange(from = 0) time: Long): String {
        return relative(time, timestamp().toLong())
    }

    fun relative(@IntRange(from = 0) from: Long, @IntRange(from = 0) to: Long): String {
        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString()
    }

    fun pickTime(activity: Activity, date: Calendar? = null, onTimeSetListener: TimePickerDialog.OnTimeSetListener) {
        val calendar = date ?: calendar()
        val is24HourFormat = DateFormat.is24HourFormat(activity)
        TimePickerDialog(activity, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show()
    }

    fun pickDate(activity: Activity, min: Calendar? = null, date: Calendar? = null, max: Calendar? = null, onDateSetListener: DatePickerDialog.OnDateSetListener) {
        val calendar = date ?: calendar() // now by default
        val datePickerDialog = DatePickerDialog(activity,
                onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        val datePicker = datePickerDialog.datePicker
        min?.let { calendar ->
            datePicker.minDate = 0 // HACK: <http://stackoverflow.com/a/19722636>
            datePicker.minDate = calendar.timeInMillis
        }
        max?.let { calendar ->
            datePicker.maxDate = calendar.timeInMillis
        }
        datePickerDialog.show()
    }

}
