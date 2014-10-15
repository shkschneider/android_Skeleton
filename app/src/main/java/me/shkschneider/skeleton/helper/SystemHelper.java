package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.os.SystemClock;

import me.shkschneider.app.MainApplication;

public class SystemHelper {

    public static final String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
    public static final String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
    public static final String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
    public static final String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
    public static final String SYSTEM_PROPERTY_USER_DIR = "user.dir";
    public static final String SYSTEM_PROPERTY_USER_REGION = "user.region";
    public static final String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
    public static final String SYSTEM_PROPERTY_JAVA_RUNTIME_NAME = "java.runtime.name";
    public static final String SYSTEM_PROPERTY_HTTP_AGENT = "http.agent";
    public static final String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
    public static final String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";
    public static final String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
    public static final String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
    public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
    public static final String SYSTEM_PROPERTY_OS_VERSION = "os.version";
    public static final String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";

    public static String systemProperty(final String property) {
        final String systemProperty = System.getProperty(property);
        if (systemProperty == null) {
            LogHelper.warning("SystemProperty was NULL");
            return null;
        }

        return systemProperty;
    }

    public static String uname() {
        return String.format("%s %s %s",
                systemProperty(SYSTEM_PROPERTY_OS_NAME),
                systemProperty(SYSTEM_PROPERTY_OS_VERSION),
                systemProperty(SYSTEM_PROPERTY_OS_ARCH));
    }

    public static final String SYSTEM_SERVICE_WINDOW_SERVICE = Context.WINDOW_SERVICE;
    public static final String SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE = Context.LAYOUT_INFLATER_SERVICE;
    public static final String SYSTEM_SERVICE_ACTIVITY_SERVICE = Context.ACTIVITY_SERVICE;
    public static final String SYSTEM_SERVICE_POWER_SERVICE = Context.POWER_SERVICE;
    public static final String SYSTEM_SERVICE_ALARM_SERVICE = Context.ALARM_SERVICE;
    public static final String SYSTEM_SERVICE_NOTIFICATION_SERVICE = Context.NOTIFICATION_SERVICE;
    public static final String SYSTEM_SERVICE_KEYGUARD_SERVICE = Context.KEYGUARD_SERVICE;
    public static final String SYSTEM_SERVICE_LOCATION_SERVICE = Context.LOCATION_SERVICE;
    public static final String SYSTEM_SERVICE_SEARCH_SERVICE = Context.SEARCH_SERVICE;
    public static final String SYSTEM_SERVICE_SENSOR = Context.SENSOR_SERVICE;
    public static final String SYSTEM_SERVICE_STORAGE = Context.STORAGE_SERVICE;
    public static final String SYSTEM_SERVICE_VIBRATOR = Context.VIBRATOR_SERVICE;
    public static final String SYSTEM_SERVICE_CONNECTIVITY = Context.CONNECTIVITY_SERVICE;
    public static final String SYSTEM_SERVICE_WIFI = Context.WIFI_SERVICE;
    public static final String SYSTEM_SERVICE_AUDIO = Context.AUDIO_SERVICE;
    // API-16+: public static final String SYSTEM_SERVICE_MEDIA_ROUTER = Context.MEDIA_ROUTER_SERVICE;
    public static final String SYSTEM_SERVICE_TELEPHONY = Context.TELEPHONY_SERVICE;
    public static final String SYSTEM_SERVICE_INPUT_METHOD = Context.INPUT_METHOD_SERVICE;
    public static final String SYSTEM_SERVICE_UI_MODE = Context.UI_MODE_SERVICE;
    public static final String SYSTEM_SERVICE_DOWNLOAD = Context.DOWNLOAD_SERVICE;

    public static Object systemService(final String service) {
        return MainApplication.CONTEXT.getSystemService(service);
    }

    public static long sinceBoot() {
        return SystemClock.elapsedRealtime();
    }

    public static long sinceCurrentThreadBirth() {
        return SystemClock.currentThreadTimeMillis();
    }

    public static void safeSleep(final long milliseconds) {
        SystemClock.sleep(milliseconds);
    }

}
