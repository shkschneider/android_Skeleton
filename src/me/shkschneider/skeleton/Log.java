package me.shkschneider.skeleton;

@SuppressWarnings("unused")
public class Log {

    public static final int VERBOSE = 10;
    public static final int DEBUG = 20;
    public static final int INFO = 30;
    public static final int WARN = 40;
    public static final int ERROR = 50;

    public static void log(final int state, final String msg) {
        final String tag = SkeletonApplication.TAG;

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

        switch (state) {
            case VERBOSE:
                android.util.Log.v(tag, "[" + stack + "] " + msg);
                break ;

            case DEBUG:
                android.util.Log.d(tag, "[" + stack + "] " + msg);
                break ;

            case INFO:
                android.util.Log.i(tag, "[" + stack + "] " + msg);
                break ;

            case WARN:
                android.util.Log.w(tag, "[" + stack + "] " + msg);
                break ;

            case ERROR:
                android.util.Log.e(tag, "[" + stack + "] " + msg);
                break ;
        }
    }

    public static void v(final String msg) {
        log(VERBOSE, msg);
    }

    public static void d(final String msg) {
        log(DEBUG, msg);
    }

    public static void i(final String msg) {
        log(INFO, msg);
    }

    public static void w(final String msg) {
        log(WARN, msg);
    }

    public static void e(final String msg) {
        log(ERROR, msg);
    }

}
