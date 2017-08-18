package me.shkschneider.skeleton.java;

public class ExceptionHelper {

    protected ExceptionHelper() {
        // Empty
    }

    public static void uncaughtException(final Callback callback) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread thread, final Throwable throwable) {
                if (callback != null) {
                    callback.uncaughtException(throwable);
                }
            }
        });
    }

    public interface Callback {

        void uncaughtException(final Throwable throwable);

    }

}
