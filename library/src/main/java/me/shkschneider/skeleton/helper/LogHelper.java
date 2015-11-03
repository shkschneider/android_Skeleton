package me.shkschneider.skeleton.helper;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import me.shkschneider.skeleton.java.ObjectHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class LogHelper {

    protected LogHelper() {
        // Empty
    }

    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARN = Log.WARN;
    private static final int ERROR = Log.ERROR;
    private static final int WTF = Log.ASSERT;

    // Used to identify the source of a log message.
    // It usually identifies the class or activity where the log call occurs.
    protected static String TAG = ApplicationHelper.packageName();
    // Here I use the application's packageName

    protected static void log(@IntRange(from=VERBOSE, to=WTF) final int level, @Nullable final String msg, final @Nullable Throwable throwable) {
        final StackTraceElement[] elements = new Throwable().getStackTrace();
        String callerClassName = "?";
        String callerMethodName = "?";
        String callerLineNumber = "?";
        if (elements.length >= 3) {
            callerClassName = elements[2].getClassName();
            callerClassName = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
            if (callerClassName.indexOf("$") > 0) {
                callerClassName = callerClassName.substring(0, callerClassName.indexOf("$"));
            }
            callerMethodName = elements[2].getMethodName();
            callerMethodName = callerMethodName.substring(callerMethodName.lastIndexOf('_') + 1);
            if (callerMethodName.equals("<init>")) {
                callerMethodName = callerClassName;
            }
            callerLineNumber = String.valueOf(elements[2].getLineNumber());
        }
        final String stack = "[" + callerClassName + "." + callerMethodName + "():" + callerLineNumber + "]" + (StringHelper.nullOrEmpty(msg) ? "" : " ");
        switch (level) {
            case VERBOSE:
                android.util.Log.v(TAG, stack + msg, throwable);
                break ;
            case DEBUG:
                android.util.Log.d(TAG, stack + msg, throwable);
                break ;
            case INFO:
                android.util.Log.i(TAG, stack + msg, throwable);
                break ;
            case WARN:
                android.util.Log.w(TAG, stack + msg, throwable);
                break ;
            case ERROR:
                android.util.Log.e(TAG, stack + msg, throwable);
                break ;
            case WTF:
                android.util.Log.wtf(TAG, stack + msg, throwable);
                break ;
            default:
                break ;
        }
    }

    public static void debug(@NonNull final Object object) {
        log(DEBUG, ObjectHelper.jsonify(object), null);
    }

    public static void debug(@NonNull final Object object, @NonNull final Throwable throwable) {
        log(DEBUG, ObjectHelper.jsonify(object), throwable);
    }

    public static void debug(@NonNull final String msg) {
        log(DEBUG, msg, null);
    }

    public static void debug(@NonNull final String msg, @NonNull final Throwable throwable) {
        log(DEBUG, msg, throwable);
    }

    public static void verbose(@NonNull final Object object) {
        log(DEBUG, ObjectHelper.jsonify(object), null);
    }

    public static void verbose(@NonNull final Object object, @NonNull final Throwable throwable) {
        log(DEBUG, ObjectHelper.jsonify(object), throwable);
    }

    public static void verbose(@NonNull final String msg) {
        log(VERBOSE, msg, null);
    }

    public static void verbose(@NonNull final String msg, @NonNull final Throwable throwable) {
        log(VERBOSE, msg, throwable);
    }

    public static void info(@NonNull final Object object) {
        log(DEBUG, ObjectHelper.jsonify(object), null);
    }

    public static void info(@NonNull final Object object, @NonNull final Throwable throwable) {
        log(DEBUG, ObjectHelper.jsonify(object), throwable);
    }

    public static void info(@NonNull final String msg) {
        log(INFO, msg, null);
    }

    public static void info(@NonNull final String msg, @NonNull final Throwable throwable) {
        log(INFO, msg, throwable);
    }

    public static void warning(@NonNull final Object object) {
        log(DEBUG, ObjectHelper.jsonify(object), null);
    }

    public static void warning(@NonNull final Object object, @NonNull final Throwable throwable) {
        log(DEBUG, ObjectHelper.jsonify(object), throwable);
    }

    public static void warning(@NonNull final String msg) {
        log(WARN, msg, null);
    }

    public static void warning(@NonNull final String msg, @NonNull final Throwable throwable) {
        log(WARN, msg, throwable);
    }

    public static void error(@NonNull final Object object) {
        log(DEBUG, ObjectHelper.jsonify(object), null);
    }

    public static void error(@NonNull final Object object, @NonNull final Throwable throwable) {
        log(DEBUG, ObjectHelper.jsonify(object), throwable);
    }

    public static void error(@NonNull final String msg) {
        log(ERROR, msg, null);
    }

    public static void error(@NonNull final String msg, @NonNull final Throwable throwable) {
        log(ERROR, msg, throwable);
    }

    public static void wtf(@NonNull final Object object) {
        log(DEBUG, ObjectHelper.jsonify(object), null);
    }

    public static void wtf(@NonNull final Object object, @NonNull final Throwable throwable) {
        log(DEBUG, ObjectHelper.jsonify(object), throwable);
    }

    public static void wtf(@NonNull final String msg) {
        log(WTF, msg, null);
    }

    public static void wtf(@NonNull final String msg, @NonNull final Throwable throwable) {
        log(WTF, msg, throwable);
    }

    public static void wtf(@NonNull final Throwable throwable) {
        log(WTF, null, throwable);
    }

}
