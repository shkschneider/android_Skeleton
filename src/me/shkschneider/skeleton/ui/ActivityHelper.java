package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class ActivityHelper {

    // API-11+: Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP
    public static final int HOME_FLAGS = Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP;

    public boolean start(final Activity activity, final Intent intent) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return false;
        }

        try {
            activity.startActivity(intent);
            return true;
        }
        catch (final ActivityNotFoundException e) {
            LogHelper.error("ActivityNotFoundException: " + e.getMessage());
            return false;
        }
    }

    public boolean start(final Activity activity, final Class<?> c) {
        return start(activity, new Intent(activity, c));
    }

    public boolean stop(final Activity activity) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        activity.finish();
        return true;
    }

    public static boolean toast(final String message) {
        if (StringHelper.nullOrEmpty(message)) {
            LogHelper.warning("Message was NULL");
            return false;
        }

        Toast.makeText(SkeletonApplication.CONTEXT, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    public static boolean popup(final Activity activity, final String title, final String message, final DialogInterface.OnClickListener onClickListener) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (StringHelper.nullOrEmpty(message)) {
            LogHelper.warning("Message was NULL");
            return false;
        }

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        if (! StringHelper.nullOrEmpty(title)) {
            alertDialogBuilder.setTitle(title);
        }
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNeutralButton(android.R.string.ok, onClickListener);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    public static boolean popup(final Activity activity, final String title, final String message) {
        return popup(activity, title, message, null);
    }

    public static AlertDialog.Builder alertDialogBuilder(final int style) {
        return new AlertDialog.Builder(new ContextThemeWrapper(SkeletonApplication.CONTEXT, style));
    }

    public static AlertDialog.Builder alertDialogBuilder() {
        return new AlertDialog.Builder(SkeletonApplication.CONTEXT);
    }

}
