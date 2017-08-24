package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ThreadHelper;

public class Toaster {

    public static void lengthShort(@NonNull final String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    public static void lengthLong(@NonNull final String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    private static void toast(@NonNull final String msg, final int duration) {
        if (TextUtils.isEmpty(msg)) {
            LogHelper.warning("Message was NULL");
        }
        ThreadHelper.foregroundThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ContextHelper.applicationContext(), msg, duration).show();
            }
        });
    }

}
