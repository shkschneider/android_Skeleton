package me.shkschneider.skeleton.android.provider

import me.shkschneider.skeleton.kotlin.jvm.tryOrNull

// <https://developer.android.com/reference/java/lang/System.html#getProperties()>
sealed class AndroidSystemProperty(val name: String) {

    object JavaIoTmpdir : AndroidSystemProperty("java.io.tmpdir") // /sdcard
    object JavaLibraryPath : AndroidSystemProperty("java.library.path") // /vendor/lib:/system/lib
    object JavaSpecificationVersion : AndroidSystemProperty("java.specification.version") // 0.9
    object JavaSpecificationVendor : AndroidSystemProperty("java.specification.vendor") // The Android Project
    object JavaSpecificationName : AndroidSystemProperty("java.specification.name") // Dalvik Core Library
    object JavaVmVersion : AndroidSystemProperty("java.vm.version") // 1.2.0
    object JavaVmVendor : AndroidSystemProperty("java.vm.vendor") // The Android Project
    object JavaVmName : AndroidSystemProperty("java.vm.name") // Dalvik
    object JavaVmSpecificationVersion : AndroidSystemProperty("java.vm.specification.version") // 0.9
    object JavaVmSpecificationVendor : AndroidSystemProperty("java.vm.specification.vendor") // The Android Project
    object JavaVmSpecificationName : AndroidSystemProperty("java.vm.specification.name") // Dalvik Virtual Machine Specification

    fun get(): String? =
        tryOrNull {
            System.getProperty(name)
        }

}
