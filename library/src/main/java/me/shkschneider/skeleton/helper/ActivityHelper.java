package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.ui.ViewHelper;

public class ActivityHelper {

    protected ActivityHelper() {
        // Empty
    }

    @Deprecated
    public static View contentView(@NonNull final Activity activity) {
        return ViewHelper.content(activity);
    }

    public static void toast(@NonNull final String msg) {
        if (StringHelper.nullOrEmpty(msg)) {
            LogHelper.warning("Message was NULL");
            return;
        }
        Toast.makeText(ApplicationHelper.context(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void snackBar(@NonNull final View view, @NonNull final String msg) {
        if (StringHelper.nullOrEmpty(msg)) {
            LogHelper.warning("Message was NULL");
            return;
        }
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBar(@NonNull final View view, @NonNull final String msg, @NonNull final String action, @NonNull final View.OnClickListener onClickListener) {
        if (StringHelper.nullOrEmpty(msg)) {
            LogHelper.warning("Message was NULL");
            return;
        }
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

    @Nullable
    public static String title(@NonNull final SkeletonActivity skeletonActivity) {
        final ActionBar actionBar = skeletonActivity.getSupportActionBar();
        if (actionBar != null) {
            final CharSequence charSequence = actionBar.getTitle();
            if (charSequence != null) {
                return charSequence.toString();
            }
        }
        return null;
    }

    @Deprecated
    @Nullable
    public static String subtitle(@NonNull final SkeletonActivity skeletonActivity) {
        final ActionBar actionBar = skeletonActivity.getSupportActionBar();
        if (actionBar != null) {
            final CharSequence charSequence = actionBar.getSubtitle();
            if (charSequence != null) {
                return charSequence.toString();
            }
        }
        return null;
    }

}
