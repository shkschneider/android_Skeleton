package me.shkschneider.skeleton.android.core.jvm

object ExceptionHelper {

    fun uncaughtException(callback: (Throwable) -> (Unit)) {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            callback(throwable)
        }
    }

}
