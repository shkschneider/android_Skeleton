package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import me.shkschneider.skeleton.helper.LogHelper;

public class Snack {

    public static void bar(@NonNull final View view, @NonNull final String msg) {
        bar(view, msg, null, null);
    }

    public static void bar(@NonNull final View view, @NonNull final String msg, final String action, final View.OnClickListener onClickListener) {
        if (TextUtils.isEmpty(msg)) {
            LogHelper.warning("Message was NULL");
        }
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        if (! TextUtils.isEmpty(action) && onClickListener != null) {
            snackbar.setAction(action, onClickListener);
        }
        snackbar.show();
    }

}
