package me.shkschneider.skeleton.ui;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

public class DialogHelper {

    public static void dimBehind(@NonNull final Window window, final boolean dim) {
        if (dim) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    public static void cancelable(@NonNull final Dialog dialog, final boolean cancelable, final boolean canceledOnTouchOutside) {
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

}
