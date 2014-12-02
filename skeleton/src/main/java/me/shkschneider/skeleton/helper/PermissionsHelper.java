package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.SkeletonApplication;

// <http://developer.android.com/reference/android/Manifest.permission.html>
public class PermissionsHelper {

    public static final String ACCESS_CHECKIN_PROPERTIES = android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES;
    public static final String ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_LOCATION_EXTRA_COMMANDS = android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
    public static final String ACCESS_MOCK_LOCATION = android.Manifest.permission.ACCESS_MOCK_LOCATION;
    public static final String ACCESS_NETWORK_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE;
    public static final String ACCESS_SURFACE_FLINGER = android.Manifest.permission.ACCESS_SURFACE_FLINGER;
    public static final String ACCESS_WIFI_STATE = android.Manifest.permission.ACCESS_WIFI_STATE;
    public static final String ACCOUNT_MANAGER = android.Manifest.permission.ACCOUNT_MANAGER;
    public static final String ADD_VOICEMAIL = android.Manifest.permission.ADD_VOICEMAIL;
    public static final String AUTHENTICATE_ACCOUNTS = android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
    public static final String BATTERY_STATS = android.Manifest.permission.BATTERY_STATS;
    @SuppressLint("InlinedApi") // API-16+
    public static final String BIND_ACCESSIBILITY_SERVICE = android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE;
    public static final String BIND_APPWIDGET = android.Manifest.permission.BIND_APPWIDGET;
    public static final String BIND_DEVICE_ADMIN = android.Manifest.permission.BIND_DEVICE_ADMIN;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BIND_DREAM_SERVICE = android.Manifest.permission.BIND_DREAM_SERVICE;
    public static final String BIND_INPUT_METHOD = android.Manifest.permission.BIND_INPUT_METHOD;
    @SuppressLint("InlinedApi") // API-19+
    public static final String BIND_NFC_SERVICE = android.Manifest.permission.BIND_NFC_SERVICE;
    @SuppressLint("InlinedApi") // API-18+
    public static final String BIND_NOTIFICATION_LISTENER_SERVICE = android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String BIND_PRINT_SERVICE = android.Manifest.permission.BIND_PRINT_SERVICE;
    public static final String BIND_REMOTEVIEWS = android.Manifest.permission.BIND_REMOTEVIEWS;
    public static final String BIND_TEXT_SERVICE = android.Manifest.permission.BIND_TEXT_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BIND_TV_INPUT = android.Manifest.permission.BIND_TV_INPUT;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BIND_VOICE_INTERACTION = android.Manifest.permission.BIND_VOICE_INTERACTION;
    public static final String BIND_VPN_SERVICE = android.Manifest.permission.BIND_VPN_SERVICE;
    public static final String BIND_WALLPAPER = android.Manifest.permission.BIND_WALLPAPER;
    public static final String BLUETOOTH = android.Manifest.permission.BLUETOOTH;
    public static final String BLUETOOTH_ADMIN = android.Manifest.permission.BLUETOOTH_ADMIN;
    @SuppressLint("InlinedApi") // API-19+
    public static final String BLUETOOTH_PRIVILEGED = android.Manifest.permission.BLUETOOTH_PRIVILEGED;
    @SuppressLint("InlinedApi") // API-20+
    public static final String BODY_SENSORS = android.Manifest.permission.BODY_SENSORS;
    public static final String BRICK = android.Manifest.permission.BRICK;
    public static final String BROADCAST_PACKAGE_REMOVED = android.Manifest.permission.BROADCAST_PACKAGE_REMOVED;
    public static final String BROADCAST_SMS = android.Manifest.permission.BROADCAST_SMS;
    public static final String BROADCAST_STICKY = android.Manifest.permission.BROADCAST_STICKY;
    public static final String BROADCAST_WAP_PUSH = android.Manifest.permission.BROADCAST_WAP_PUSH;
    public static final String CALL_PHONE = android.Manifest.permission.CALL_PHONE;
    public static final String CALL_PRIVILEGED = android.Manifest.permission.CALL_PRIVILEGED;
    public static final String CAMERA = android.Manifest.permission.CAMERA;
    @SuppressLint("InlinedApi") // API-19+
    public static final String CAPTURE_AUDIO_OUTPUT = android.Manifest.permission.CAPTURE_AUDIO_OUTPUT;
    @SuppressLint("InlinedApi") // API-19+
    public static final String CAPTURE_SECURE_VIDEO_OUTPUT = android.Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT;
    @SuppressLint("InlinedApi") // API-19+
    public static final String CAPTURE_VIDEO_OUTPUT = android.Manifest.permission.CAPTURE_VIDEO_OUTPUT;
    public static final String CHANGE_COMPONENT_ENABLED_STATE = android.Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE;
    public static final String CHANGE_CONFIGURATION = android.Manifest.permission.CHANGE_CONFIGURATION;
    public static final String CHANGE_NETWORK_STATE = android.Manifest.permission.CHANGE_NETWORK_STATE;
    public static final String CHANGE_WIFI_MULTICAST_STATE = android.Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
    public static final String CHANGE_WIFI_STATE = android.Manifest.permission.CHANGE_WIFI_STATE;
    public static final String CLEAR_APP_CACHE = android.Manifest.permission.CLEAR_APP_CACHE;
    public static final String CLEAR_APP_USER_DATA = android.Manifest.permission.CLEAR_APP_USER_DATA;
    public static final String CONTROL_LOCATION_UPDATES = android.Manifest.permission.CONTROL_LOCATION_UPDATES;
    public static final String DELETE_CACHE_FILES = android.Manifest.permission.DELETE_CACHE_FILES;
    public static final String DELETE_PACKAGES = android.Manifest.permission.DELETE_PACKAGES;
    public static final String DEVICE_POWER = android.Manifest.permission.DEVICE_POWER;
    public static final String DIAGNOSTIC = android.Manifest.permission.DIAGNOSTIC;
    public static final String DISABLE_KEYGUARD = android.Manifest.permission.DISABLE_KEYGUARD;
    public static final String DUMP = android.Manifest.permission.DUMP;
    public static final String EXPAND_STATUS_BAR = android.Manifest.permission.EXPAND_STATUS_BAR;
    public static final String FACTORY_TEST = android.Manifest.permission.FACTORY_TEST;
    public static final String FLASHLIGHT = android.Manifest.permission.FLASHLIGHT;
    public static final String FORCE_BACK = android.Manifest.permission.FORCE_BACK;
    public static final String GET_ACCOUNTS = android.Manifest.permission.GET_ACCOUNTS;
    public static final String GET_PACKAGE_SIZE = android.Manifest.permission.GET_PACKAGE_SIZE;
    @Deprecated
    public static final String GET_TASKS = android.Manifest.permission.GET_TASKS;
    @SuppressLint("InlinedApi") // API-18+
    public static final String GET_TOP_ACTIVITY_INFO = android.Manifest.permission.GET_TOP_ACTIVITY_INFO;
    public static final String GLOBAL_SEARCH = android.Manifest.permission.GLOBAL_SEARCH;
    public static final String HARDWARE_TEST = android.Manifest.permission.HARDWARE_TEST;
    public static final String INJECT_EVENTS = android.Manifest.permission.INJECT_EVENTS;
    public static final String INSTALL_LOCATION_PROVIDER = android.Manifest.permission.INSTALL_LOCATION_PROVIDER;
    public static final String INSTALL_PACKAGES = android.Manifest.permission.INSTALL_PACKAGES;
    @SuppressLint("InlinedApi") // API-19+
    public static final String INSTALL_SHORTCUT = android.Manifest.permission.INSTALL_SHORTCUT;
    public static final String INTERNAL_SYSTEM_WINDOW = android.Manifest.permission.INTERNAL_SYSTEM_WINDOW;
    public static final String INTERNET = android.Manifest.permission.INTERNET;
    public static final String KILL_BACKGROUND_PROCESSES = android.Manifest.permission.KILL_BACKGROUND_PROCESSES;
    @SuppressLint("InlinedApi") // API-18+
    public static final String LOCATION_HARDWARE = android.Manifest.permission.LOCATION_HARDWARE;
    public static final String MANAGE_ACCOUNTS = android.Manifest.permission.MANAGE_ACCOUNTS;
    public static final String MANAGE_APP_TOKENS = android.Manifest.permission.MANAGE_APP_TOKENS;
    @SuppressLint("InlinedApi") // API-19+
    public static final String MANAGE_DOCUMENTS = android.Manifest.permission.MANAGE_DOCUMENTS;
    public static final String MASTER_CLEAR = android.Manifest.permission.MASTER_CLEAR;
    @SuppressLint("InlinedApi") // API-19+
    public static final String MEDIA_CONTENT_CONTROL = android.Manifest.permission.MEDIA_CONTENT_CONTROL;
    public static final String MODIFY_AUDIO_SETTINGS = android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
    public static final String MODIFY_PHONE_STATE = android.Manifest.permission.MODIFY_PHONE_STATE;
    public static final String MOUNT_FORMAT_FILESYSTEMS = android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS;
    public static final String MOUNT_UNMOUNT_FILESYSTEMS = android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
    public static final String NFC = android.Manifest.permission.NFC;
    @Deprecated
    public static final String PERSISTENT_ACTIVITY = android.Manifest.permission.PERSISTENT_ACTIVITY;
    public static final String PROCESS_OUTGOING_CALLS = android.Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String READ_CALENDAR = android.Manifest.permission.READ_CALENDAR;
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_CALL_LOG = android.Manifest.permission.READ_CALL_LOG;
    public static final String READ_CONTACTS = android.Manifest.permission.READ_CONTACTS;
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String READ_FRAME_BUFFER = android.Manifest.permission.READ_FRAME_BUFFER;
    public static final String READ_HISTORY_BOOKMARKS = android.Manifest.permission.READ_HISTORY_BOOKMARKS;
    @Deprecated
    public static final String READ_INPUT_STATE = android.Manifest.permission.READ_INPUT_STATE;
    public static final String READ_LOGS = android.Manifest.permission.READ_LOGS;
    public static final String READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;
    public static final String READ_PROFILE = android.Manifest.permission.READ_PROFILE;
    public static final String READ_SMS = android.Manifest.permission.READ_SMS;
    @SuppressLint("InlinedApi") // API-15+
    @Deprecated
    public static final String READ_SOCIAL_STREAM = android.Manifest.permission.READ_SOCIAL_STREAM;
    public static final String READ_SYNC_SETTINGS = android.Manifest.permission.READ_SYNC_SETTINGS;
    public static final String READ_SYNC_STATS = android.Manifest.permission.READ_SYNC_STATS;
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_USER_DICTIONARY = android.Manifest.permission.READ_USER_DICTIONARY;
    @SuppressLint("InlinedApi") // API-21+
    public static final String READ_VOICEMAIL = android.Manifest.permission.READ_VOICEMAIL;
    public static final String REBOOT = android.Manifest.permission.REBOOT;
    public static final String RECEIVE_BOOT_COMPLETED = android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
    public static final String RECEIVE_MMS = android.Manifest.permission.RECEIVE_MMS;
    public static final String RECEIVE_SMS = android.Manifest.permission.RECEIVE_SMS;
    public static final String RECEIVE_WAP_PUSH = android.Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO;
    public static final String REORDER_TASKS = android.Manifest.permission.REORDER_TASKS;
    @Deprecated
    public static final String RESTART_PACKAGES = android.Manifest.permission.RESTART_PACKAGES;
    @SuppressLint("InlinedApi") // API-18+
    public static final String SEND_RESPOND_VIA_MESSAGE = android.Manifest.permission.SEND_RESPOND_VIA_MESSAGE;
    public static final String SEND_SMS = android.Manifest.permission.SEND_SMS;
    public static final String SET_ACTIVITY_WATCHER = android.Manifest.permission.SET_ACTIVITY_WATCHER;
    public static final String SET_ALARM = android.Manifest.permission.SET_ALARM;
    public static final String SET_ALWAYS_FINISH = android.Manifest.permission.SET_ALWAYS_FINISH;
    public static final String SET_ANIMATION_SCALE = android.Manifest.permission.SET_ANIMATION_SCALE;
    public static final String SET_DEBUG_APP = android.Manifest.permission.SET_DEBUG_APP;
    public static final String SET_ORIENTATION = android.Manifest.permission.SET_ORIENTATION;
    public static final String SET_POINTER_SPEED = android.Manifest.permission.SET_POINTER_SPEED;
    @Deprecated
    public static final String SET_PREFERRED_APPLICATIONS = android.Manifest.permission.SET_PREFERRED_APPLICATIONS;
    public static final String SET_PROCESS_LIMIT = android.Manifest.permission.SET_PROCESS_LIMIT;
    public static final String SET_TIME = android.Manifest.permission.SET_TIME;
    public static final String SET_TIME_ZONE = android.Manifest.permission.SET_TIME_ZONE;
    public static final String SET_WALLPAPER = android.Manifest.permission.SET_WALLPAPER;
    public static final String SET_WALLPAPER_HINTS = android.Manifest.permission.SET_WALLPAPER_HINTS;
    public static final String SIGNAL_PERSISTENT_PROCESSES = android.Manifest.permission.SIGNAL_PERSISTENT_PROCESSES;
    public static final String STATUS_BAR = android.Manifest.permission.STATUS_BAR;
    public static final String SUBSCRIBED_FEEDS_READ = android.Manifest.permission.SUBSCRIBED_FEEDS_READ;
    public static final String SUBSCRIBED_FEEDS_WRITE = android.Manifest.permission.SUBSCRIBED_FEEDS_WRITE;
    public static final String SYSTEM_ALERT_WINDOW = android.Manifest.permission.SYSTEM_ALERT_WINDOW;
    @SuppressLint("InlinedApi") // API-19+
    public static final String TRANSMIT_IR = android.Manifest.permission.TRANSMIT_IR;
    @SuppressLint("InlinedApi") // API-19+
    public static final String UNINSTALL_SHORTCUT = android.Manifest.permission.UNINSTALL_SHORTCUT;
    public static final String UPDATE_DEVICE_STATS = android.Manifest.permission.UPDATE_DEVICE_STATS;
    public static final String USE_CREDENTIALS = android.Manifest.permission.USE_CREDENTIALS;
    public static final String USE_SIP = android.Manifest.permission.USE_SIP;
    public static final String VIBRATE = android.Manifest.permission.VIBRATE;
    public static final String WAKE_LOCK = android.Manifest.permission.WAKE_LOCK;
    public static final String WRITE_APN_SETTINGS = android.Manifest.permission.WRITE_APN_SETTINGS;
    public static final String WRITE_CALENDAR = android.Manifest.permission.WRITE_CALENDAR;
    @SuppressLint("InlinedApi") // API-16+
    public static final String WRITE_CALL_LOG = android.Manifest.permission.WRITE_CALL_LOG;
    public static final String WRITE_CONTACTS = android.Manifest.permission.WRITE_CONTACTS;
    public static final String WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String WRITE_GSERVICES = android.Manifest.permission.WRITE_GSERVICES;
    public static final String WRITE_HISTORY_BOOKMARKS = android.Manifest.permission.WRITE_HISTORY_BOOKMARKS;
    public static final String WRITE_PROFILE = android.Manifest.permission.WRITE_PROFILE;
    public static final String WRITE_SECURE_SETTINGS = android.Manifest.permission.WRITE_SECURE_SETTINGS;
    public static final String WRITE_SETTINGS = android.Manifest.permission.WRITE_SETTINGS;
    public static final String WRITE_SMS = android.Manifest.permission.WRITE_SMS;
    @SuppressLint("InlinedApi") // API-15+
    @Deprecated
    public static final String WRITE_SOCIAL_STREAM = android.Manifest.permission.WRITE_SOCIAL_STREAM;
    public static final String WRITE_SYNC_SETTINGS = android.Manifest.permission.WRITE_SYNC_SETTINGS;
    @SuppressLint("InlinedApi") // API-16+
    public static final String WRITE_USER_DICTIONARY = android.Manifest.permission.WRITE_USER_DICTIONARY;
    @SuppressLint("InlinedApi") // API-21+
    public static final String WRITE_VOICEMAIL = android.Manifest.permission.WRITE_VOICEMAIL;

    public static boolean permission(@NonNull final String permission) {
        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        return (packageManager.checkPermission(permission, ApplicationHelper.packageName()) == PackageManager.PERMISSION_GRANTED);
    }

}
