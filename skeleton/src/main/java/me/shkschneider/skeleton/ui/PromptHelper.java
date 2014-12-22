package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import me.shkschneider.skeleton.helpers.KeyboardHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

import java.util.Calendar;

public class PromptHelper {

    public static boolean date(@NonNull final Activity activity, @NonNull final Callback callback) {
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(final DatePicker view, final int year, final int month, final int day) {
                callback.promptCallback(String.format("%04d/%02d/%02d", year, month + 1, day));
            }

        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                .show();
        return true;
    }

    public static boolean time(@NonNull final Activity activity, @NonNull final Callback callback) {
        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(final TimePicker timePicker, final int hour, final int minute) {
                callback.promptCallback(String.format("%02d:%02d", hour, minute));
            }

        },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(activity))
                .show();
        return true;
    }

    public static boolean string(@NonNull final Activity activity, @NonNull final String title, @NonNull final Callback callback) {
        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        final EditText editText = new EditText(activity);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(editText);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        if (! StringHelper.nullOrEmpty(title)) {
            alertDialogBuilder.setTitle(title);
        }
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                final Editable editable = editText.getText();
                if (editable == null) {
                    LogHelper.warning("Editable was NULL");
                    return ;
                }

                callback.promptCallback(editable.toString());
            }

        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        KeyboardHelper.show(alertDialog.getWindow(), editText);
        return true;
    }

    private static int VALUE;

    public static boolean number(@NonNull final Activity activity, @NonNull final String title, final int min, final int value, final int max, @NonNull final Callback callback) {
        if (min < 0 || min >= max) {
            LogHelper.warning("Min/Max are invalid");
            return false;
        }
        if (value < min || value > max) {
            LogHelper.warning("Value is invalid");
            return false;
        }

        VALUE = value;

        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        final TextView textView = new TextView(activity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTypeface(null, Typeface.BOLD);
        linearLayout.addView(textView);
        final SeekBar seekBar = new SeekBar(activity);
        seekBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(seekBar);

        textView.setText(String.valueOf(VALUE));
        seekBar.setProgress(VALUE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                VALUE = progress;
                textView.setText(String.valueOf(VALUE));
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
                // Ignored
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                // Ignored
            }

        });

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        if (! StringHelper.nullOrEmpty(title)) {
            alertDialogBuilder.setTitle(title);
        }
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                callback.promptCallback(String.valueOf(VALUE));
            }

        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    public static boolean number(@NonNull final Activity activity, @NonNull final String title, final int min, final int max, @NonNull final Callback callback) {
        return number(activity, title, min, 0, max, callback);
    }

    public static boolean choice(@NonNull final Activity activity, @NonNull final String title, @NonNull final String[] choices, int checked, @NonNull final Callback callback) {
        if (checked < -1 || checked > (choices.length - 1)) {
            LogHelper.debug("Default was invalid");
            checked = -1;
        }

        VALUE = checked;

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        if (! StringHelper.nullOrEmpty(title)) {
            alertDialogBuilder.setTitle(title);
        }
        alertDialogBuilder.setSingleChoiceItems(choices, checked, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                VALUE = i;
            }

        });
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                callback.promptCallback((VALUE >= 0 ? choices[VALUE] : null));
            }

        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    public static boolean choice(@NonNull final Activity activity, @NonNull final String title, @NonNull final String[] choices, @NonNull final Callback callback) {
        return choice(activity, title, choices, -1, callback);
    }

    public static interface Callback {

        public void promptCallback(final String s);

    }

}
