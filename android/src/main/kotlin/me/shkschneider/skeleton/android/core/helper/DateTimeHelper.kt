package me.shkschneider.skeleton.android.core.helper

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.annotation.IntRange
import java.util.Calendar

object DateTimeHelper {

    // <http://developer.android.com/intl/ru/reference/java/text/SimpleDateFormat.html>
    const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    // <https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1>
    const val HTTP_DATE = "EEE, dd MMM yyyy HH:mm:ss z"
    val RFC_3339 = if (AndroidHelper.api() >= AndroidHelper.ANDROID_7) "yyyy-MM-dd'T'HH:mm:ssXXX" else "yyyy-MM-dd'T'HH:mm:ssZ"

    fun relative(@IntRange(from = 0) time: Long): String {
        return relative(time, time)
    }

    fun relative(@IntRange(from = 0) from: Long, @IntRange(from = 0) to: Long): String {
        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString()
    }

    fun pickTime(activity: Activity, date: Calendar? = null, onTimeSetListener: TimePickerDialog.OnTimeSetListener) {
        val calendar = date ?: Calendar.getInstance()
        val is24HourFormat = DateFormat.is24HourFormat(activity)
        TimePickerDialog(activity, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show()
    }

    fun pickDate(activity: Activity, min: Calendar? = null, date: Calendar? = null, max: Calendar? = null, onDateSetListener: DatePickerDialog.OnDateSetListener) {
        val calendar = date ?: Calendar.getInstance() // now by default
        val datePickerDialog = DatePickerDialog(activity,
                onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        val datePicker = datePickerDialog.datePicker
        min?.let {
            datePicker.minDate = 0 // HACK: <http://stackoverflow.com/a/19722636>
            datePicker.minDate = it.timeInMillis
        }
        max?.let {
            datePicker.maxDate = it.timeInMillis
        }
        datePickerDialog.show()
    }

}
