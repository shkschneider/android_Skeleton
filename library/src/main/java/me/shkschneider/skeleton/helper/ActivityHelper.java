package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import me.shkschneider.skeleton.SkeletonActivity;

public class ActivityHelper {

    protected ActivityHelper() {
        // Empty
    }

    public static View contentView(@NonNull final Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Deprecated
    public static void toast(@NonNull final String msg) {
        Toast.makeText(ApplicationHelper.context(), msg, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public static void snackBar(@NonNull final View view, @NonNull final String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Deprecated
    public static void snackBar(@NonNull final View view, @NonNull final String msg, @NonNull final String action, @NonNull final View.OnClickListener onClickListener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction(action, onClickListener)
                .show();
    }

    public static boolean portrait() {
        return (ApplicationHelper.resources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public static boolean landscape() {
        return (ApplicationHelper.resources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    public static String title(@NonNull final Activity activity) {
        if (activity instanceof SkeletonActivity) {
            final ActionBar actionBar = ((SkeletonActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                final CharSequence charSequence = actionBar.getTitle();
                if (charSequence != null) {
                    return charSequence.toString();
                }
            }
        }
        return null;
    }

    @Deprecated
    public static String subtitle(@NonNull final Activity activity) {
        if (activity instanceof SkeletonActivity) {
            final ActionBar actionBar = ((SkeletonActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                final CharSequence charSequence = actionBar.getSubtitle();
                if (charSequence != null) {
                    return charSequence.toString();
                }
            }
        }
        return null;
    }

}
