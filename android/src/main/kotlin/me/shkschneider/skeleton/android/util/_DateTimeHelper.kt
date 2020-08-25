package me.shkschneider.skeleton.android.util

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.annotation.IntRange
import me.shkschneider.skeleton.android.os.AndroidHelper
import me.shkschneider.skeleton.kotlin.util.DateTimeHelper
import java.util.Calendar

val DateTimeHelper.RFC_3339
    get() =
        if (AndroidHelper.api() >= AndroidHelper.ANDROID_7) "yyyy-MM-dd'T'HH:mm:ssXXX"
        else "yyyy-MM-dd'T'HH:mm:ssZ"

fun DateTimeHelper.relative(@IntRange(from = 0) time: Long): String =
    relative(time, time)

fun DateTimeHelper.relative(@IntRange(from = 0) from: Long, @IntRange(from = 0) to: Long): String =
    DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString()

fun DateTimeHelper.pickTime(activity: Activity, date: Calendar? = null, onTimeSetListener: TimePickerDialog.OnTimeSetListener) {
    val calendar = date ?: Calendar.getInstance()
    val is24HourFormat = DateFormat.is24HourFormat(activity)
    TimePickerDialog(activity, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show()
}

fun DateTimeHelper.pickDate(activity: Activity, min: Calendar? = null, date: Calendar? = null, max: Calendar? = null, onDateSetListener: DatePickerDialog.OnDateSetListener) {
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
