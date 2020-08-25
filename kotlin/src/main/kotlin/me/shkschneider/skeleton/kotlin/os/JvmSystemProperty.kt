package me.shkschneider.skeleton.kotlin.os

import me.shkschneider.skeleton.kotlin.jvm.tryOrNull

// <https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>
sealed class JvmSystemProperty(val name: String) {
    
    object FileSeparator : JvmSystemProperty("file.separator")
    object JavaClassPath : JvmSystemProperty("java.class.path")
    object JavaHome : JvmSystemProperty("java.home")
    object JavaVendor : JvmSystemProperty("java.vendor")
    object JavaVendorUrl : JvmSystemProperty("java.vendor.url")
    object JavaVersion : JvmSystemProperty("java.version")
    object LineSeparator : JvmSystemProperty("line.separator")
    object OsArch : JvmSystemProperty("os.arch")
    object OsName : JvmSystemProperty("os.name")
    object OsVersion : JvmSystemProperty("os.version")
    object PathSeparator : JvmSystemProperty("path.separator")
    object UserDir : JvmSystemProperty("user.dir")
    object UserHome : JvmSystemProperty("user.home")
    object UserName : JvmSystemProperty("user.name")
    
    fun get(): String? =
        get(name)

    companion object {

        fun get(name: String) =
            tryOrNull {
                System.getProperty(name)
            }

    }
    
}
