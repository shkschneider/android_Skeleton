package me.shkschneider.skeleton.kotlin.helper

import me.shkschneider.skeleton.kotlin.log.Logger

// <https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>
object SystemProperties {

    const val FILE_SEPARATOR = "file.separator"
    const val JAVA_CLASS_PATH = "java.class.path"
    const val JAVA_HOME = "java.home"
    const val JAVA_VENDOR = "java.vendor"
    const val JAVA_VENDOR_URL = "java.vendor.url"
    const val JAVA_VERSION = "java.version"
    const val LINE_SEPARATOR = "line.separator"
    const val OS_ARCH = "os.arch"
    const val OS_NAME = "os.name"
    const val OS_VERSION = "os.version"
    const val PATH_SEPARATOR = "path.separator"
    const val USER_DIR = "user.dir"
    const val USER_HOME = "user.home"
    const val USER_NAME = "user.name"

    fun get(property: String): String? =
        System.getProperty(property) ?: run {
            Logger.warning("SystemProperty was NULL")
            return null
        }

}
