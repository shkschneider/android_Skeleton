package me.shkschneider.skeleton.helperx.log

interface ILogger {

    fun debug(msg: String, throwable: Throwable?)
    fun debug(msg: String)

    fun verbose(msg: String, throwable: Throwable?)
    fun verbose(msg: String)

    fun info(msg: String, throwable: Throwable?)
    fun info(msg: String)

    fun warning(msg: String, throwable: Throwable?)
    fun warning(msg: String)

    fun error(msg: String, throwable: Throwable?)
    fun error(msg: String)

    fun wtf(throwable: Throwable)

}
