package me.shkschneider.skeleton.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

// <https://developer.android.com/reference/java/lang/System.html#getProperties()>
public class SystemProperties {

    protected SystemProperties() {
        // Empty
    }

    public static final String FILE_SEPARATOR = "file.separator"; // /
    public static final String JAVA_CLASS_PATH = "java.class.path"; // .
    // java.class.version
    // java.compiler
    // java.ext.dirs
    public static final String JAVA_HOME = "java.home"; // /system
    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir"; // /sdcard
    public static final String JAVA_LIBRARY_PATH = "java.library.path"; // /vendor/lib:/system/lib
    public static final String JAVA_VENDOR = "java.vendor"; // The Android Project
    public static final String JAVA_VENDOR_URL = "java.vendor.url"; // http://www.android.com
    // java.version
    public static final String JAVA_SPECIFICATION_VERSION = "java.specification.version"; // 0.9
    public static final String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor"; // The Android Project
    public static final String JAVA_SPECIFICATION_NAME = "java.specification.name"; // Dalvik Core Library
    public static final String JAVA_VM_VERSION = "java.vm.version"; // 1.2.0
    public static final String JAVA_VM_VENDOR = "java.vm.vendor"; // The Android Project
    public static final String JAVA_VM_NAME = "java.vm.name"; // Dalvik
    public static final String JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version"; // 0.9
    public static final String JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor"; // The Android Project
    public static final String JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name"; // Dalvik Virtual Machine Specification
    public static final String LINE_SEPARATOR = "line.separator"; // \n
    public static final String OS_ARCH = "os.arch"; // armv7l
    public static final String OS_NAME = "os.name"; // Linux
    public static final String OS_VERSION = "os.version"; // 2.6.32.9-g103d848
    public static final String PATH_SEPARATOR = "path.separator"; // :
    public static final String USER_DIR = "user.dir"; // /
    // user.home
    // user.name
    
    @Nullable
    public static String get(@NonNull final String property) {
        final String systemProperty = System.getProperty(property);
        if (TextUtils.isEmpty(property)) {
            LogHelper.warning("SystemProperty was NULL");
            return null;
        }
        return systemProperty;
    }

}
