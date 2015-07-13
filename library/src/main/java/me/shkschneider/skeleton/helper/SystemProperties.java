package me.shkschneider.skeleton.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// <http://developer.android.com/reference/java/lang/System.html#getProperty(java.lang.String)>
public class SystemProperties {

    protected SystemProperties() {
        // Empty
    }

    public static final String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
    public static final String SYSTEM_PROPERTY_JAVA_CLASS_PATH = "java.class.path";
    public static final String SYSTEM_PROPERTY_JAVA_CLASS_VERSION = "java.class.version";
    public static final String SYSTEM_PROPERTY_JAVA_COMPILER = "java.compiler";
    public static final String SYSTEM_PROPERTY_JAVA_EXT_DIRS = "java.ext.dirs";
    public static final String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
    public static final String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
    public static final String SYSTEM_PROPERTY_JAVA_LIBRARY_PATH = "java.library.path";
    public static final String SYSTEM_PROPERTY_JAVA_VENDOR = "java.vendor";
    public static final String SYSTEM_PROPERTY_JAVA_VENDOR_URL = "java.vendor.url";
    public static final String SYSTEM_PROPERTY_JAVA_VERSION = "java.version";
    public static final String SYSTEM_PROPERTY_JAVA_SPECIFICATION_VERSION = "java.specification.version";
    public static final String SYSTEM_PROPERTY_JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
    public static final String SYSTEM_PROPERTY_JAVA_SPECIFICATION_NAME = "java.specification.name";
    public static final String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
    public static final String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
    public static final String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
    public static final String SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
    public static final String SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";
    public static final String SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";
    public static final String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
    public static final String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
    public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
    public static final String SYSTEM_PROPERTY_OS_VERSION = "os.version";
    public static final String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";
    public static final String SYSTEM_PROPERTY_USER_DIR = "user.dir";
    public static final String SYSTEM_PROPERTY_USER_HOME = "user.home";
    public static final String SYSTEM_PROPERTY_USER_NAME = "user.name";

    @Nullable
    public static String property(@NonNull final String property) {
        final String systemProperty = System.getProperty(property);
        if (systemProperty == null) {
            LogHelper.w("SystemProperty was NULL");
            return null;
        }
        return systemProperty;
    }

}
