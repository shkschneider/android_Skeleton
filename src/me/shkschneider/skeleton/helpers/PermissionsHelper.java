package me.shkschneider.skeleton.helpers;

import android.content.pm.PackageManager;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class PermissionsHelper {

    public static final String ACCESS_CHECKIN_PROPERTIES = android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES;
    public static final String ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_LOCATION_EXTRA_COMMANDS = android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
    public static final String ACCESS_MOCK_LOCATION = android.Manifest.permission.ACCESS_MOCK_LOCATION;
    public static final String ACCESS_NETWORK_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE;
    public static final String ACCESS_SURFACE_FLINGER = android.Manifest.permission.ACCESS_SURFACE_FLINGER;
    public static final String ACCESS_WIFI_STATE = android.Manifest.permission.ACCESS_WIFI_STATE;
    public static final String ACCOUNT_MANAGER = android.Manifest.permission.ACCOUNT_MANAGER;
    // API-14+: public static final String ADD_VOICEMAIL = android.Manifest.permission.ADD_VOICEMAIL;
    public static final String AUTHENTICATE_ACCOUNTS = android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
    public static final String BATTERY_STATS = android.Manifest.permission.BATTERY_STATS;
    // API-16+: public static final String BIND_ACCESSIBILITY_SERVICE = android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE;
    public static final String BIND_APPWIDGET = android.Manifest.permission.BIND_APPWIDGET;
    public static final String BIND_DEVICE_ADMIN = android.Manifest.permission.BIND_DEVICE_ADMIN;
    public static final String BIND_INPUT_METHOD = android.Manifest.permission.BIND_INPUT_METHOD;
    public static final String BIND_WALLPAPER = android.Manifest.permission.BIND_WALLPAPER;
    public static final String BLUETOOTH = android.Manifest.permission.BLUETOOTH;
    public static final String BLUETOOTH_ADMIN = android.Manifest.permission.BLUETOOTH_ADMIN;
    public static final String BRICK = android.Manifest.permission.BRICK;
    public static final String BROADCAST_PACKAGE_REMOVED = android.Manifest.permission.BROADCAST_PACKAGE_REMOVED;
    public static final String BROADCAST_SMS = android.Manifest.permission.BROADCAST_SMS;
    public static final String BROADCAST_STICKY = android.Manifest.permission.BROADCAST_STICKY;
    public static final String BROADCAST_WAP_PUSH = android.Manifest.permission.BROADCAST_WAP_PUSH;
    public static final String CALL_PHONE = android.Manifest.permission.CALL_PHONE;
    public static final String CALL_PRIVILEGED = android.Manifest.permission.CALL_PRIVILEGED;
    public static final String CAMERA = android.Manifest.permission.CAMERA;
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
    public static final String GET_TASKS = android.Manifest.permission.GET_TASKS;
    // API-18+: public static final String GET_TOP_ACTIVITY_INFO = android.Manifest.permission.GET_TOP_ACTIVITY_INFO;
    public static final String GLOBAL_SEARCH = android.Manifest.permission.GLOBAL_SEARCH;
    public static final String HARDWARE_TEST = android.Manifest.permission.HARDWARE_TEST;
    public static final String INJECT_EVENTS = android.Manifest.permission.INJECT_EVENTS;
    public static final String INSTALL_LOCATION_PROVIDER = android.Manifest.permission.INSTALL_LOCATION_PROVIDER;
    public static final String INSTALL_PACKAGES = android.Manifest.permission.INSTALL_PACKAGES;
    public static final String INTERNAL_SYSTEM_WINDOW = android.Manifest.permission.INTERNAL_SYSTEM_WINDOW;
    public static final String INTERNET = android.Manifest.permission.INTERNET;
    public static final String KILL_BACKGROUND_PROCESSES = android.Manifest.permission.KILL_BACKGROUND_PROCESSES;
    // API-18+: public static final String LOCATION_HARDWARE = android.Manifest.permission.LOCATION_HARDWARE;
    public static final String MANAGE_ACCOUNTS = android.Manifest.permission.MANAGE_ACCOUNTS;
    public static final String MANAGE_APP_TOKENS = android.Manifest.permission.MANAGE_APP_TOKENS;
    public static final String MASTER_CLEAR = android.Manifest.permission.MASTER_CLEAR;
    public static final String MODIFY_AUDIO_SETTINGS = android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
    public static final String MODIFY_PHONE_STATE = android.Manifest.permission.MODIFY_PHONE_STATE;
    public static final String MOUNT_FORMAT_FILESYSTEMS = android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS;
    public static final String MOUNT_UNMOUNT_FILESYSTEMS = android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
    public static final String NFC = android.Manifest.permission.NFC;

    public static Boolean permission(final String permission) {
        if (StringHelper.nullOrEmpty(permission)) {
            LogHelper.warning("Permission was NULL");
            return null;
        }

        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        return (packageManager.checkPermission(permission, ApplicationHelper.packageName()) == PackageManager.PERMISSION_GRANTED);
    }

}
