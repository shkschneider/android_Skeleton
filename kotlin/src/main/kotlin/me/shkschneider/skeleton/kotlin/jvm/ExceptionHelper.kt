package me.shkschneider.skeleton.kotlin.jvm

import kotlin.system.exitProcess

object ExceptionHelper {

    fun uncaughtException(callback: (Throwable) -> (Unit)) {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            callback(throwable)
//            Process.killProcess(Process.myPid());
            exitProcess(1);
        }
    }

}
