package me.shkschneider.skeleton.helperx.log

object JvmLogger : ILogger {

    private fun log(msg: String, throwable: Throwable?) {
        var fingerprint = Logger.fingerprint
        throwable?.printStackTrace()
        msg.split("\n").forEach { line ->
            println(fingerprint + line)
        }
    }

    override fun debug(msg: String, throwable: Throwable?) =
            log(msg, throwable)

    override fun debug(msg: String) = debug(msg, null)

    override fun verbose(msg: String, throwable: Throwable?) =
            log(msg, throwable)

    override fun verbose(msg: String) = verbose(msg, null)

    override fun info(msg: String, throwable: Throwable?) =
            log(msg, throwable)

    override fun info(msg: String) = info(msg, null)

    override fun warning(msg: String, throwable: Throwable?) =
            log(msg, throwable)

    override fun warning(msg: String) = warning(msg, null)

    override fun error(msg: String, throwable: Throwable?) =
            log(msg, throwable)

    override fun error(msg: String) = error(msg, null)

    override fun wtf(throwable: Throwable) = throwable.printStackTrace()

}
