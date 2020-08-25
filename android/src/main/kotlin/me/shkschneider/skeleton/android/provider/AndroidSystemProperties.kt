package me.shkschneider.skeleton.android.provider

import me.shkschneider.skeleton.kotlin.log.Logger

// <https://developer.android.com/reference/java/lang/System.html#getProperties()>
object AndroidSystemProperties {

    const val FILE_SEPARATOR = "file.separator" // /
    const val JAVA_CLASS_PATH = "java.class.path" // .
    // java.class.version
    // java.compiler
    // java.ext.dirs
    const val JAVA_HOME = "java.home" // /system
    const val JAVA_IO_TMPDIR = "java.io.tmpdir" // /sdcard
    const val JAVA_LIBRARY_PATH = "java.library.path" // /vendor/lib:/system/lib
    const val JAVA_VENDOR = "java.vendor" // The Android Project
    const val JAVA_VENDOR_URL = "java.vendor.url" // http://www.android.com
    // java.version
    const val JAVA_SPECIFICATION_VERSION = "java.specification.version" // 0.9
    const val JAVA_SPECIFICATION_VENDOR = "java.specification.vendor" // The Android Project
    const val JAVA_SPECIFICATION_NAME = "java.specification.name" // Dalvik Core Library
    const val JAVA_VM_VERSION = "java.vm.version" // 1.2.0
    const val JAVA_VM_VENDOR = "java.vm.vendor" // The Android Project
    const val JAVA_VM_NAME = "java.vm.name" // Dalvik
    const val JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version" // 0.9
    const val JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor" // The Android Project
    const val JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name" // Dalvik Virtual Machine Specification
    const val LINE_SEPARATOR = "line.separator" // \n
    const val OS_ARCH = "os.arch" // armv7l
    const val OS_NAME = "os.name" // Linux
    const val OS_VERSION = "os.version" // 2.6.32.9-g103d848
    const val PATH_SEPARATOR = "path.separator" // :
    const val USER_DIR = "user.dir" // /
    // user.home
    // user.name

    fun get(property: String): String? =
        System.getProperty(property) ?: run {
            Logger.warning("SystemProperty was NULL")
            null
        }

}
