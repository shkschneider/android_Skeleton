package me.shkschneider.skeleton.helpers;

import android.util.Log;

import me.shkschneider.skeleton.java.StringHelper;

public class LogHelper {

    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARN = Log.WARN;
    private static final int ERROR = Log.ERROR;
    private static final int WTF = Log.ASSERT;

    private static String TAG = ApplicationHelper.packageName();
    private static int LEVEL = (ApplicationHelper.debug() ? VERBOSE : INFO);

    private static void log(final int level, final String msg) {
        if (level < LEVEL) {
            return ;
        }
        if (StringHelper.nullOrEmpty(msg)) {
            return ;
        }

        // Uses StackTrace to build the log tag
        final StackTraceElement[] elements = new Throwable().getStackTrace();
        String callerClassName = "?";
        String callerMethodName = "?";
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
        }

        final String stack = callerClassName + " " + callerMethodName + "()";
        switch (level) {
            case VERBOSE: Log.v(TAG, "[" + stack + "] " + msg); break ;
            case DEBUG: Log.d(TAG, "[" + stack + "] " + msg); break ;
            case INFO: Log.i(TAG, "[" + stack + "] " + msg); break ;
            case WARN: Log.w(TAG, "[" + stack + "] " + msg); break ;
            case ERROR: Log.e(TAG, "[" + stack + "] " + msg); break ;
            case WTF: Log.wtf(TAG, "[" + stack + "] " + msg); break ;
            default: break ;
        }
    }

    public static void debug(final String msg) {
        LogHelper.log(DEBUG, msg);
    }

    public static void verbose(final String msg) {
        LogHelper.log(VERBOSE, msg);
    }

    public static void info(final String msg) {
        LogHelper.log(INFO, msg);
    }

    public static void warning(final String msg) {
        LogHelper.log(WARN, msg);
    }

    public static void error(final String msg) {
        LogHelper.log(ERROR, msg);
    }

    public static void wtf(final String msg) {
        LogHelper.log(WTF, msg);
    }

    public static void wtf(final Throwable throwable) {
        if (throwable == null) {
            return ;
        }

        LogHelper.log(WTF, throwable.getClass().getName() + ": " + throwable.getMessage());
        if (ApplicationHelper.debug()) {
            throwable.printStackTrace();
        }
    }

}
