package me.shkschneider.skeleton.helper;

import android.app.ActivityOptions;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.View;

import me.shkschneider.skeleton.ui.Snack;
import me.shkschneider.skeleton.ui.Toaster;

public class ActivityHelper {

    protected ActivityHelper() {
        // Empty
    }

    public static boolean portrait() {
        return (ApplicationHelper.resources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public static boolean landscape() {
        return (ApplicationHelper.resources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Deprecated
    public static void toast(@NonNull final String msg) {
        Toaster.lengthShort(msg);
    }

    @Deprecated
    public static void snackBar(@NonNull final View view, @NonNull final String msg) {
        Snack.bar(view, msg);
    }

    @Deprecated
    public static void snackBar(@NonNull final View view, @NonNull final String msg, @NonNull final String action, @NonNull final View.OnClickListener onClickListener) {
        Snack.bar(view, msg, action, onClickListener);
    }

    // <https://developer.android.com/reference/android/app/ActivityOptions.html>

    public static ActivityOptions pendingTransition(int in, int out) {
        return ActivityOptions.makeCustomAnimation(ContextHelper.applicationContext(), in, out);
    }

    public static ActivityOptions pendingTransition() {
        return pendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
