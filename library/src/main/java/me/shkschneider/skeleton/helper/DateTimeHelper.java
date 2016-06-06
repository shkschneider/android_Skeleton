package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import me.shkschneider.skeleton.java.StringHelper;

public class DateTimeHelper {

    private static class CalendarHolder {

        private static Calendar INSTANCE = Calendar.getInstance(LocaleHelper.locale());

    }

    public static Calendar calendar() {
        return CalendarHolder.INSTANCE;
    }

    // <http://developer.android.com/intl/ru/reference/java/text/SimpleDateFormat.html>
    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    protected DateTimeHelper() {
        // Empty
    }

    public static int day() {
        return calendar().get(Calendar.DAY_OF_MONTH);
    }

    public static int month() {
        return calendar().get(Calendar.MONTH) + 1;
    }

    public static int year() {
        return calendar().get(Calendar.YEAR);
    }

    // <http://howtodoinjava.com/2012/12/16/always-use-setlenient-false-when-using-simpledateformat-for-date-validation-in-java/>
    public static String format(@NonNull final Calendar calendar, String format) {
        if (StringHelper.nullOrEmpty(format)) {
            format = ISO8601;
        }
        calendar.setLenient(false);
        return new SimpleDateFormat(format, LocaleHelper.locale()).format(calendar.getTime());
    }

    public static long now() {
        return System.currentTimeMillis();
    }

    public static long timestamp() {
        return calendar().getTimeInMillis() / 1000;
    }

    public static long timestamp(final String string) {
        if (StringHelper.nullOrEmpty(string)) {
            return 0;
        }
        try {
            final Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", LocaleHelper.locale()).parse(string);
            return (date.getTime() / 1000);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return 0;
        }
    }

    public static int gmtOffset() {
        return Math.round(TimeZone.getDefault().getOffset(calendar().getTimeInMillis()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static String relative(@IntRange(from=0) final long time) {
        return relative(time, timestamp());
    }

    public static String relative(@IntRange(from=0) final long from, @IntRange(from=0) final long to) {
        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

    public static void pickTime(@NonNull final Activity activity, final Calendar date, @NonNull final TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        final Calendar calendar = (date != null ? date : calendar());
        final boolean is24HourFormat = DateFormat.is24HourFormat(ApplicationHelper.context());
        new TimePickerDialog(activity, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show();
    }

    public static void pickDate(@NonNull final Activity activity, final Calendar min, final Calendar date, final Calendar max, @NonNull final DatePickerDialog.OnDateSetListener onDateSetListener) {
        final Calendar calendar = (date != null ? date : calendar()); // now by default
        final DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        final DatePicker datePicker = datePickerDialog.getDatePicker();
        if (min != null) {
            datePicker.setMinDate(0); // HACK: <http://stackoverflow.com/a/19722636>
            datePicker.setMinDate(min.getTimeInMillis());
        }
        if (max != null) {
            datePicker.setMaxDate(max.getTimeInMillis());
        }
        datePickerDialog.show();
    }

}
