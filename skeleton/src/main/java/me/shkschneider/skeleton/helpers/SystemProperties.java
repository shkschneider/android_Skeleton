package me.shkschneider.skeleton.helpers;

import android.support.annotation.NonNull;

public class SystemProperties {

    private static String property(@NonNull final String property) {
        final String systemProperty = System.getProperty(property);
        if (systemProperty == null) {
            LogHelper.warning("SystemProperty was NULL");
            return null;
        }

        return systemProperty;
    }

    public static String javaVmName() {
        return property("java.vm.name");
    }
    public static String javaVmVendor() {
        return property("java.vm.vendor");
    }
    public static String javaVmVersion() {
        return property("java.vm.version");
    }
    public static String javaHome() {
        return property("java.home");
    }
    public static String userDir() {
        return property("user.dir");
    }
    public static String userRegion() {
        return property("user.region");
    }
    public static String javaIoTmpdir() {
        return property("java.io.tmpdir");
    }
    public static String javaRuntimeName() {
        return property("java.runtime.name");
    }
    public static String httpAgent() {
        return property("http.agent");
    }
    public static String fileSeparator() {
        return property("file.separator");
    }
    public static String fileEncoding() {
        return property("file.encoding");
    }
    public static String lineSeparator() {
        return property("line.separator");
    }
    public static String osArch() {
        return property("os.arch");
    }
    public static String osName() {
        return property("os.name");
    }
    public static String osVersion() {
        return property("os.version");
    }
    public static String pathSeparator() {
        return property("path.separator");
    }

}
