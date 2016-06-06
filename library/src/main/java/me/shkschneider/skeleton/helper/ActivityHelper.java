package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class ActivityHelper {

    protected ActivityHelper() {
        // Empty
    }

    public static void toast(@NonNull final String msg) {
        if (TextUtils.isEmpty(msg)) {
            LogHelper.warning("Message was NULL");
            return;
        }
        Toast.makeText(ApplicationHelper.context(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void snackBar(@NonNull final View view, @NonNull final String msg) {
        if (TextUtils.isEmpty(msg)) {
            LogHelper.warning("Message was NULL");
            return;
        }
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBar(@NonNull final View view, @NonNull final String msg, @NonNull final String action, @NonNull final View.OnClickListener onClickListener) {
        if (TextUtils.isEmpty(msg)) {
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

}
