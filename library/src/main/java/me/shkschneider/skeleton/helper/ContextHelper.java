package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * applicationContext()
 */
public class ContextHelper {

    @SuppressLint("StaticFieldLeak")
    private static Context CONTEXT;

    @Deprecated // Avoid
    public static void applicationContext(@NonNull final Context context) {
        CONTEXT = context;
    }

    public static Context applicationContext() {
        if (CONTEXT == null) {
            throw new NullPointerException("Context was not set!");
        }
        return CONTEXT;
    }

}
