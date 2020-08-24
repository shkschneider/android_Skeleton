package me.shkschneider.skeleton.kotlin.jvm

object ExceptionHelper {

    fun uncaughtException(callback: (Throwable) -> (Unit)) {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            callback(throwable)
        }
    }

}
