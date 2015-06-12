package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

// <http://developer.android.com/reference/android/content/pm/PackageManager.html>
public class FeaturesHelper {

    protected FeaturesHelper() {
        // Empty
    }

    @SuppressLint("InlinedApi") // API-18+
    public static final String FEATURE_APP_WIDGETS = PackageManager.FEATURE_APP_WIDGETS;
    public static final String FEATURE_AUDIO_LOW_LATENCY = PackageManager.FEATURE_AUDIO_LOW_LATENCY;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_AUDIO_OUTPUT = PackageManager.FEATURE_AUDIO_OUTPUT;
    @SuppressLint("InlinedApi") // API-20+
    public static final String FEATURE_BACKUP = PackageManager.FEATURE_BACKUP;
    public static final String FEATURE_BLUETOOTH = PackageManager.FEATURE_BLUETOOTH;
    @SuppressLint("InlinedApi") // API-18+
    public static final String FEATURE_BLUETOOTH_LE = PackageManager.FEATURE_BLUETOOTH_LE;
    public static final String FEATURE_CAMERA = PackageManager.FEATURE_CAMERA;
    @SuppressLint("InlinedApi") // API-17+
    public static final String FEATURE_CAMERA_ANY = PackageManager.FEATURE_CAMERA_ANY;
    public static final String FEATURE_CAMERA_AUTOFOCUS = PackageManager.FEATURE_CAMERA_AUTOFOCUS;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING = PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR = PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_CAMERA_CAPABILITY_RAW = PackageManager.FEATURE_CAMERA_CAPABILITY_RAW;
    @SuppressLint("InlinedApi") // API-20+
    public static final String FEATURE_CAMERA_EXTERNAL = PackageManager.FEATURE_CAMERA_EXTERNAL;
    public static final String FEATURE_CAMERA_FLASH = PackageManager.FEATURE_CAMERA_FLASH;
    public static final String FEATURE_CAMERA_FRONT = PackageManager.FEATURE_CAMERA_FRONT;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_CAMERA_LEVEL_FULL = PackageManager.FEATURE_CAMERA_LEVEL_FULL;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_CONNECTION_SERVICE = PackageManager.FEATURE_CONNECTION_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String FEATURE_CONSUMER_IR = PackageManager.FEATURE_CONSUMER_IR;
    @SuppressLint("InlinedApi") // API-19+
    public static final String FEATURE_DEVICE_ADMIN = PackageManager.FEATURE_DEVICE_ADMIN;
    public static final String FEATURE_FAKETOUCH = PackageManager.FEATURE_FAKETOUCH;
    public static final String FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT;
    public static final String FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_GAMEPAD = PackageManager.FEATURE_GAMEPAD;
    @SuppressLint("InlinedApi") // API-18+
    public static final String FEATURE_HOME_SCREEN = PackageManager.FEATURE_HOME_SCREEN;
    @SuppressLint("InlinedApi") // API-18+
    public static final String FEATURE_INPUT_METHODS = PackageManager.FEATURE_INPUT_METHODS;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_LEANBACK = PackageManager.FEATURE_LEANBACK;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_LIVE_TV = PackageManager.FEATURE_LIVE_TV;
    public static final String FEATURE_LIVE_WALLPAPER = PackageManager.FEATURE_LIVE_WALLPAPER;
    public static final String FEATURE_LOCATION = PackageManager.FEATURE_LOCATION;
    public static final String FEATURE_LOCATION_GPS = PackageManager.FEATURE_LOCATION_GPS;
    public static final String FEATURE_LOCATION_NETWORK = PackageManager.FEATURE_LOCATION_NETWORK;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_MANAGED_USERS = PackageManager.FEATURE_MANAGED_USERS;
    public static final String FEATURE_MICROPHONE = PackageManager.FEATURE_MICROPHONE;
    public static final String FEATURE_NFC = PackageManager.FEATURE_NFC;
    @SuppressLint("InlinedApi") // API-19+
    public static final String FEATURE_NFC_HOST_CARD_EMULATION = PackageManager.FEATURE_NFC_HOST_CARD_EMULATION;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_OPENGLES_EXTENSION_PACK = PackageManager.FEATURE_OPENGLES_EXTENSION_PACK;
    @SuppressLint("InlinedApi") // API-20+
    public static final String FEATURE_PRINTING = PackageManager.FEATURE_PRINTING;
    public static final String FEATURE_SCREEN_LANDSCAPE = PackageManager.FEATURE_SCREEN_LANDSCAPE;
    public static final String FEATURE_SCREEN_PORTRAIT = PackageManager.FEATURE_SCREEN_PORTRAIT;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_SECURELY_REMOVES_USERS = PackageManager.FEATURE_SECURELY_REMOVES_USERS;
    public static final String FEATURE_SENSOR_ACCELEROMETER = PackageManager.FEATURE_SENSOR_ACCELEROMETER;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_SENSOR_AMBIENT_TEMPERATURE = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE;
    public static final String FEATURE_SENSOR_BAROMETER = PackageManager.FEATURE_SENSOR_BAROMETER;
    public static final String FEATURE_SENSOR_COMPASS = PackageManager.FEATURE_SENSOR_COMPASS;
    public static final String FEATURE_SENSOR_GYROSCOPE = PackageManager.FEATURE_SENSOR_GYROSCOPE;
    @SuppressLint("InlinedApi") // API-20+
    public static final String FEATURE_SENSOR_HEART_RATE = PackageManager.FEATURE_SENSOR_HEART_RATE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_SENSOR_HEART_RATE_ECG = PackageManager.FEATURE_SENSOR_HEART_RATE_ECG;
    public static final String FEATURE_SENSOR_LIGHT = PackageManager.FEATURE_SENSOR_LIGHT;
    public static final String FEATURE_SENSOR_PROXIMITY = PackageManager.FEATURE_SENSOR_PROXIMITY;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_SENSOR_RELATIVE_HUMIDITY = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY;
    @SuppressLint("InlinedApi") // API-19+
    public static final String FEATURE_SENSOR_STEP_COUNTER = PackageManager.FEATURE_SENSOR_STEP_COUNTER;
    @SuppressLint("InlinedApi") // API-19+
    public static final String FEATURE_SENSOR_STEP_DETECTOR = PackageManager.FEATURE_SENSOR_STEP_DETECTOR;
    public static final String FEATURE_SIP = PackageManager.FEATURE_SIP;
    public static final String FEATURE_SIP_VOIP = PackageManager.FEATURE_SIP_VOIP;
    public static final String FEATURE_TELEPHONY = PackageManager.FEATURE_TELEPHONY;
    public static final String FEATURE_TELEPHONY_CDMA = PackageManager.FEATURE_TELEPHONY_CDMA;
    public static final String FEATURE_TELEPHONY_GSM = PackageManager.FEATURE_TELEPHONY_GSM;
    @SuppressLint("InlinedApi") // API-16+
    @Deprecated
    public static final String FEATURE_TELEVISION = PackageManager.FEATURE_TELEVISION;
    public static final String FEATURE_TOUCHSCREEN = PackageManager.FEATURE_TOUCHSCREEN;
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH;
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT;
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND;
    public static final String FEATURE_USB_ACCESSORY = PackageManager.FEATURE_USB_ACCESSORY;
    public static final String FEATURE_USB_HOST = PackageManager.FEATURE_USB_HOST;
    @SuppressLint("InlinedApi") // API-21+
    public static final String FEATURE_VERIFIED_BOOT = PackageManager.FEATURE_VERIFIED_BOOT;
    @SuppressLint("InlinedApi") // API-20+
    public static final String FEATURE_WATCH = PackageManager.FEATURE_WATCH;
    @SuppressLint("InlinedApi") // API-20+
    public static final String FEATURE_WEBVIEW = PackageManager.FEATURE_WEBVIEW;
    public static final String FEATURE_WIFI = PackageManager.FEATURE_WIFI;
    public static final String FEATURE_WIFI_DIRECT = PackageManager.FEATURE_WIFI_DIRECT;

    public static boolean feature(@NonNull final String feature) {
        final PackageManager packageManager = ApplicationHelper.context().getPackageManager();
        if (packageManager == null) {
            Log.w("PackageManager was NULL");
            return false;
        }

        return packageManager.hasSystemFeature(feature);
    }

}
