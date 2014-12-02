package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.SkeletonApplication;

// <http://developer.android.com/reference/java/lang/System.html#getProperty(java.lang.String)>
// <http://developer.android.com/reference/android/content/Context.html>
public class SystemHelper {

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

    public static String systemProperty(@NonNull final String property) {
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

    public static final String SYSTEM_SERVICE_ACCESSIBILITY = Context.ACCESSIBILITY_SERVICE;
    public static final String SYSTEM_SERVICE_ACCOUNT = Context.ACCOUNT_SERVICE;
    public static final String SYSTEM_SERVICE_ACTIVITY = Context.ACTIVITY_SERVICE;
    public static final String SYSTEM_SERVICE_ALARM = Context.ALARM_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_APPWIDGET = Context.APPWIDGET_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String SYSTEM_SERVICE_APP_OPS = Context.APP_OPS_SERVICE;
    public static final String SYSTEM_SERVICE_AUDIO = Context.AUDIO_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_BATTERY = Context.BATTERY_SERVICE;
    @SuppressLint("InlinedApi") // API-18+
    public static final String SYSTEM_SERVICE_BLUETOOTH = Context.BLUETOOTH_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_CAMERA = Context.CAMERA_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String SYSTEM_SERVICE_CAPTIONING = Context.CAPTIONING_SERVICE;
    public static final String SYSTEM_SERVICE_CLIPBOARD = Context.CLIPBOARD_SERVICE;
    public static final String SYSTEM_SERVICE_CONNECTIVITY = Context.CONNECTIVITY_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String SYSTEM_SERVICE_CONSUMER_IR = Context.CONSUMER_IR_SERVICE;
    public static final String SYSTEM_SERVICE_DEVICE_POLICY = Context.DEVICE_POLICY_SERVICE;
    @SuppressLint("InlinedApi") // API-17+
    public static final String SYSTEM_SERVICE_DISPLAY = Context.DISPLAY_SERVICE;
    public static final String SYSTEM_SERVICE_DOWNLOAD = Context.DOWNLOAD_SERVICE;
    public static final String SYSTEM_SERVICE_DROPBOX = Context.DROPBOX_SERVICE;
    public static final String SYSTEM_SERVICE_INPUT_METHOD = Context.INPUT_METHOD_SERVICE;
    @SuppressLint("InlinedApi") // API-16+
    public static final String SYSTEM_SERVICE_INPUT = Context.INPUT_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_JOB_SCHEDULER = Context.JOB_SCHEDULER_SERVICE;
    public static final String SYSTEM_SERVICE_KEYGUARD = Context.KEYGUARD_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_LAUNCHER_APPS = Context.LAUNCHER_APPS_SERVICE;
    public static final String SYSTEM_SERVICE_LAYOUT_INFLATER = Context.LAYOUT_INFLATER_SERVICE;
    public static final String SYSTEM_SERVICE_LOCATION = Context.LOCATION_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_MEDIA_PROJECTION = Context.MEDIA_PROJECTION_SERVICE;
    @SuppressLint("InlinedApi") // API-16+
    public static final String SYSTEM_SERVICE_MEDIA_ROUTER = Context.MEDIA_ROUTER_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_MEDIA_SESSION = Context.MEDIA_SESSION_SERVICE;
    public static final String SYSTEM_SERVICE_NFC = Context.NFC_SERVICE;
    public static final String SYSTEM_SERVICE_NOTIFICATION = Context.NOTIFICATION_SERVICE;
    @SuppressLint("InlinedApi") // API-16+
    public static final String SYSTEM_SERVICE_NSD = Context.NSD_SERVICE;
    public static final String SYSTEM_SERVICE_POWER = Context.POWER_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_RESTRICTIONS = Context.RESTRICTIONS_SERVICE;
    public static final String SYSTEM_SERVICE_SEARCH = Context.SEARCH_SERVICE;
    public static final String SYSTEM_SERVICE_SENSOR = Context.SENSOR_SERVICE;
    public static final String SYSTEM_SERVICE_STORAGE = Context.STORAGE_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_TELECOM = Context.TELECOM_SERVICE;
    public static final String SYSTEM_SERVICE_TELEPHONY = Context.TELEPHONY_SERVICE;
    public static final String SYSTEM_SERVICE_TEXT_SERVICES_MANAGER = Context.TEXT_SERVICES_MANAGER_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String SYSTEM_SERVICE_TV_INPUT = Context.TV_INPUT_SERVICE;
    public static final String SYSTEM_SERVICE_UI_MODE = Context.UI_MODE_SERVICE;
    public static final String SYSTEM_SERVICE_USB = Context.USB_SERVICE;
    @SuppressLint("InlinedApi") // API-17+
    public static final String SYSTEM_SERVICE_USER = Context.USER_SERVICE;
    public static final String SYSTEM_SERVICE_VIBRATOR = Context.VIBRATOR_SERVICE;
    public static final String SYSTEM_SERVICE_WALLPAPER = Context.WALLPAPER_SERVICE;
    public static final String SYSTEM_SERVICE_WIFI_P2P = Context.WIFI_P2P_SERVICE;
    public static final String SYSTEM_SERVICE_WIFI = Context.WIFI_SERVICE;
    public static final String SYSTEM_SERVICE_WINDOW = Context.WINDOW_SERVICE;

    public static Object systemService(@NonNull final String service) {
        return SkeletonApplication.CONTEXT.getSystemService(service);
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
