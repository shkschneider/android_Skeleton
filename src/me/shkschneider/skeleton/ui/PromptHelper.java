package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Editable;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import me.shkschneider.skeleton.helpers.KeyboardHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class PromptHelper {

    private static final int DEFAULT_INT = -1;
    private static final String DEFAULT_STRING = null;

    public static boolean string(final Activity activity, final String title, final Callback callback) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

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
                if (callback == null) {
                    LogHelper.warning("callback was NULL");
                    return ;
                }

                callback.promptCallback(DEFAULT_INT, editable.toString());
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

    public static boolean number(final Activity activity, final String title, final int min, final int value, final int max, final Callback callback) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return true;
        }
        if (min < 0 || min >= max) {
            LogHelper.warning("Min/Max are invalid");
            return true;
        }
        if (value < min || value > max) {
            LogHelper.warning("Value is invalid");
            return true;
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
                if (callback == null) {
                    LogHelper.warning("callback was NULL");
                    return ;
                }

                callback.promptCallback(VALUE, String.valueOf(VALUE));
            }

        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    public static boolean number(final Activity activity, final String title, final int min, final int max, final Callback callback) {
        return number(activity, title, min, 0, max, callback);
    }

    public static boolean choice(final Activity activity, final String title, final String[] choices, int checked, final Callback callback) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (checked < -1 || checked > (choices.length - 1)) {
            LogHelper.debug("Default was invalid");
            checked = DEFAULT_INT;
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
                final String choice = (VALUE >= 0 ? choices[VALUE] : DEFAULT_STRING);
                if (callback == null) {
                    LogHelper.warning("callback was NULL");
                    return ;
                }

                callback.promptCallback(VALUE, choice);
            }

        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    public static boolean choice(final Activity activity, final String title, final String[] choices, final Callback callback) {
        return choice(activity, title, choices, DEFAULT_INT, callback);
    }

    public static interface Callback {

        public void promptCallback(final int i, final String s);

    }

}
