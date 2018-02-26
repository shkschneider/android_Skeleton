package me.shkschneider.skeleton.helper

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.annotation.IntRange
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.format.DateUtils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

object DateTimeHelper {

    // <http://developer.android.com/intl/ru/reference/java/text/SimpleDateFormat.html>
    const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    // <https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1>
    const val HTTP_DATE = "EEE, dd MMM yyyy HH:mm:ss z"

    private var _calendar: Calendar? = null

    @Synchronized
    fun calendar(): Calendar {
        if (_calendar == null) {
            _calendar = Calendar.getInstance(LocaleHelper.locale())
        }
        return _calendar!!
    }

    fun epoch(): Calendar {
        val calendar = calendar()
        calendar.timeInMillis = 0L
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
        val format: String = if (TextUtils.isEmpty(f)) ISO_8601 else f!!
        calendar.isLenient = false
        return SimpleDateFormat(format, LocaleHelper.locale()).format(calendar.time)
    }

    fun now(): Long {
        return System.currentTimeMillis()
    }

    fun timestamp(): Long {
        return now() / 1000
    }

    fun gmtOffset(): Int {
        return Math.round((TimeZone.getDefault().getOffset(calendar().timeInMillis) / DateUtils.SECOND_IN_MILLIS).toFloat())
    }

    fun relative(@IntRange(from = 0) time: Long): String {
        return relative(time, timestamp())
    }

    fun relative(@IntRange(from = 0) from: Long, @IntRange(from = 0) to: Long): String {
        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString()
    }

    fun pickTime(activity: Activity, date: Calendar?, onTimeSetListener: TimePickerDialog.OnTimeSetListener) {
        val calendar = date ?: calendar()
        val is24HourFormat = DateFormat.is24HourFormat(activity)
        TimePickerDialog(activity, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show()
    }

    fun pickDate(activity: Activity, min: Calendar?, date: Calendar?, max: Calendar?, onDateSetListener: DatePickerDialog.OnDateSetListener) {
        val calendar = date ?: calendar() // now by default
        val datePickerDialog = DatePickerDialog(activity,
                onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        val datePicker = datePickerDialog.datePicker
        if (min != null) {
            datePicker.minDate = 0 // HACK: <http://stackoverflow.com/a/19722636>
            datePicker.minDate = min.timeInMillis
        }
        if (max != null) {
            datePicker.maxDate = max.timeInMillis
        }
        datePickerDialog.show()
    }

}
