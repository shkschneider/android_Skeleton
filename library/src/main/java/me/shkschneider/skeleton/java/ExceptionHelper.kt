package me.shkschneider.skeleton.java

object ExceptionHelper {

    fun uncaughtException(callback: Callback) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            callback.uncaughtException(throwable)
        }
    }

    interface Callback {

        fun uncaughtException(throwable: Throwable)

    }

}
