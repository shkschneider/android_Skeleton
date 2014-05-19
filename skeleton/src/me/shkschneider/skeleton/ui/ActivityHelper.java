package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.ApplicationHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

import org.jetbrains.annotations.NotNull;

public class ActivityHelper {

    public static final int MAIN_FLAGS = ((AndroidHelper.api() >= AndroidHelper.API_11)
            ? (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            : (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    public static final int HOME_FLAGS = Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP;

    public static boolean toast(@NotNull final String message) {
        Toast.makeText(SkeletonApplication.CONTEXT, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    public static boolean popup(@NotNull final Activity activity, final String title, @NotNull final String message,
                                final String negative, final String neutral, final String positive,
                                final DialogInterface.OnClickListener onClickListener) {
        if (StringHelper.nullOrEmpty(negative + neutral + positive)) {
            LogHelper.warning("Negative+Neutral+Positive was NULL");
            return false;
        }

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        if (! StringHelper.nullOrEmpty(title)) {
            alertDialogBuilder.setTitle(title);
        }
        alertDialogBuilder.setMessage(message);
        if (! StringHelper.nullOrEmpty(negative)) {
            alertDialogBuilder.setNegativeButton(negative, onClickListener);
        }
        if (! StringHelper.nullOrEmpty(neutral)) {
            alertDialogBuilder.setNeutralButton(neutral, onClickListener);
        }
        if (! StringHelper.nullOrEmpty(positive)) {
            alertDialogBuilder.setPositiveButton(positive, onClickListener);
        }
        // alertDialogBuilder.setCancelable(false);
        if (onClickListener != null) {
            alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(final DialogInterface dialogInterface) {
                    // BUTTON_CANCEL = 0
                    onClickListener.onClick(dialogInterface, 0);
                }

            });
        }

        try {
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return alertDialog.isShowing();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static boolean popup(@NotNull final Activity activity, final String title, @NotNull final String message,
                                final String negative, final String positive,
                                final DialogInterface.OnClickListener onClickListener) {
        return popup(activity, title, message, negative, null, positive, onClickListener);
    }

    public static boolean popup(@NotNull final Activity activity, final String title, @NotNull final String message,
                                final DialogInterface.OnClickListener onClickListener) {
        return popup(activity, title, message, null, ApplicationHelper.resources().getString(android.R.string.ok), null, onClickListener);
    }

    public static AlertDialog.Builder alertDialogBuilder(final int style) {
        return new AlertDialog.Builder(new ContextThemeWrapper(SkeletonApplication.CONTEXT, style));
    }

    public static AlertDialog.Builder alertDialogBuilder() {
        return new AlertDialog.Builder(SkeletonApplication.CONTEXT);
    }

    public static boolean fromLauncher(@NotNull final Activity activity) {
        if (! activity.isTaskRoot()) {
            Intent intent = activity.getIntent();
            if (intent != null && intent.hasCategory(Intent.CATEGORY_LAUNCHER)) {
                String action = intent.getAction();
                if (action != null && action.equals(Intent.ACTION_MAIN)) {
                    return true;
                }
            }
        }
        return false;
    }

}
