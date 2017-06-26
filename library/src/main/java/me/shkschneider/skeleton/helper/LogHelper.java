package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import me.shkschneider.skeleton.BuildConfig;
import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.ObjectHelper;
import me.shkschneider.skeleton.java.SkHide;

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

    @SkHide
    private static String tag() {
        String tag = SkeletonApplication.TAG;
        if (TextUtils.isEmpty(tag)) {
            tag = BuildConfig.APPLICATION_ID;
        }
        while (tag.length() > 23 && tag.contains(".")) {
            tag = tag.substring(tag.indexOf(".") + 1, tag.length());
        }
        if (tag.length() > 23) {
            tag = tag.substring(tag.length() - 23, tag.length());
        }
        return tag;
    }

    @SuppressLint("LogConditional")
    private static void log(@IntRange(from = VERBOSE, to = WTF) final int level, @Nullable final String msg, @Nullable final Throwable throwable) {
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
        final String stack = "[" + callerClassName + "." + callerMethodName + "():" + callerLineNumber + "]" + (TextUtils.isEmpty(msg) ? "" : " ");
        switch (level) {
            case VERBOSE:
                Log.v(tag(), stack + msg, throwable);
                break ;
            case DEBUG:
                Log.d(tag(), stack + msg, throwable);
                break ;
            case INFO:
                Log.i(tag(), stack + msg, throwable);
                break ;
            case WARN:
                Log.w(tag(), stack + msg, throwable);
                break ;
            case ERROR:
                Log.e(tag(), stack + msg, throwable);
                break ;
            case WTF:
                Log.wtf(tag(), stack + msg, throwable);
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
        log(WTF, throwable.getMessage(), throwable);
    }

}
