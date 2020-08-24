package me.shkschneider.skeleton.kotlin.log

object Logger : ILogger {

    val fingerprint: String
        get() = Throwable().stackTrace.dropWhile { it.className == this.javaClass.name }.run {
            this[0]?.let { element ->
                val parent = element.className.substringAfterLast(".").substringBefore("$")
                val child = element.methodName.substringBefore("$").takeIf { it != "invoke" } ?: "$"
                return@run "[$parent.$child():${element.lineNumber}] "
            }.orEmpty()
        }

    private val loggers = listOf<ILogger>(
        JvmLogger
    )

    // Debug logs are compiled in but stripped at runtime.
    override fun debug(msg: String, throwable: Throwable?) =
            loggers.forEach { it.debug(msg, throwable) }

    override fun debug(msg: String) =
            loggers.forEach { it.debug(msg) }

    override fun verbose(msg: String, throwable: Throwable?) =
            loggers.forEach { it.verbose(msg) }

    override fun verbose(msg: String) =
            loggers.forEach { it.verbose(msg) }

    override fun info(msg: String, throwable: Throwable?) =
            loggers.forEach { it.info(msg) }

    override fun info(msg: String) =
            loggers.forEach { it.info(msg) }

    override fun warning(msg: String, throwable: Throwable?) =
            loggers.forEach { it.warning(msg) }

    override fun warning(msg: String) =
            loggers.forEach { it.warning(msg) }

    override fun error(msg: String, throwable: Throwable?) =
            loggers.forEach { it.error(msg) }

    override fun error(msg: String) =
            loggers.forEach { it.error(msg) }

    // Useful to log exceptions (avoids Exception.printStackTrace())
    override fun wtf(throwable: Throwable) =
            loggers.forEach { it.wtf(throwable) }

}
