package me.shkschneider.skeleton.kotlin.javax

object ExceptionHelper {

    fun uncaughtException(callback: (Throwable) -> (Unit)) {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            callback(throwable)
        }
    }

}
