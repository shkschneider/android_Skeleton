package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * applicationContext()
 */
public class ContextHelper {

    @SuppressLint("StaticFieldLeak")
    private static Context CONTEXT;

    @Deprecated // Discouraged
    public static void applicationContext(@NonNull final Context context) {
        if (! (context instanceof Application)) {
            LogHelper.warning("Context is supposed to be Application's!");
        }
        CONTEXT = context;
    }

    public static Context applicationContext() {
        if (CONTEXT == null) {
            throw new NullPointerException("Context was not set!");
        }
        return CONTEXT;
    }

}
