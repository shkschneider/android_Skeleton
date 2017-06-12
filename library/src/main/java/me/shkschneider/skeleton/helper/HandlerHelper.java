package me.shkschneider.skeleton.helper;

import android.os.Handler;
import android.os.Looper;

public class HandlerHelper {

    protected HandlerHelper() {
        // Empty
    }

    public static Handler main() {
        return new Handler(Looper.getMainLooper());
    }

}
