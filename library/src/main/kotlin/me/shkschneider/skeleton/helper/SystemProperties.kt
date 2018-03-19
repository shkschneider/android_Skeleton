package me.shkschneider.skeleton.helper

// <https://developer.android.com/reference/java/lang/System.html#getProperties()>
object SystemProperties {

    val FILE_SEPARATOR = "file.separator" // /
    val JAVA_CLASS_PATH = "java.class.path" // .
    // java.class.version
    // java.compiler
    // java.ext.dirs
    val JAVA_HOME = "java.home" // /system
    val JAVA_IO_TMPDIR = "java.io.tmpdir" // /sdcard
    val JAVA_LIBRARY_PATH = "java.library.path" // /vendor/lib:/system/lib
    val JAVA_VENDOR = "java.vendor" // The Android Project
    val JAVA_VENDOR_URL = "java.vendor.url" // http://www.android.com
    // java.version
    val JAVA_SPECIFICATION_VERSION = "java.specification.version" // 0.9
    val JAVA_SPECIFICATION_VENDOR = "java.specification.vendor" // The Android Project
    val JAVA_SPECIFICATION_NAME = "java.specification.name" // Dalvik Core Library
    val JAVA_VM_VERSION = "java.vm.version" // 1.2.0
    val JAVA_VM_VENDOR = "java.vm.vendor" // The Android Project
    val JAVA_VM_NAME = "java.vm.name" // Dalvik
    val JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version" // 0.9
    val JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor" // The Android Project
    val JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name" // Dalvik Virtual Machine Specification
    val LINE_SEPARATOR = "line.separator" // \n
    val OS_ARCH = "os.arch" // armv7l
    val OS_NAME = "os.name" // Linux
    val OS_VERSION = "os.version" // 2.6.32.9-g103d848
    val PATH_SEPARATOR = "path.separator" // :
    val USER_DIR = "user.dir" // /
    // user.home
    // user.name

    fun get(property: String): String? {
        val systemProperty = System.getProperty(property)
        if (property.isEmpty()) {
            Logger.warning("SystemProperty was NULL")
            return null
        }
        return systemProperty
    }

}
