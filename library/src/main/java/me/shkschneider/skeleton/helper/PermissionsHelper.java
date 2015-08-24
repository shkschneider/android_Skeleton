package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.java.ArrayHelper;

// <http://developer.android.com/reference/android/Manifest.permission.html>
public class PermissionsHelper {

    private static final int REQUEST_CODE = 23;

    protected PermissionsHelper() {
        // Empty
    }

    // Useless: only used to keep track of new features
    public static final String ACCESS_CHECKIN_PROPERTIES = Manifest.permission.ACCESS_CHECKIN_PROPERTIES;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION; // DANGEROUS
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION; // DANGEROUS
    public static final String ACCESS_LOCATION_EXTRA_COMMANDS = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
    public static final String ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    @SuppressLint("InlinedApi") // API-23+
    public static final String ACCESS_NOTIFICATION_POLICY = Manifest.permission.ACCESS_NOTIFICATION_POLICY;
    public static final String ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE;
    public static final String ACCOUNT_MANAGER = Manifest.permission.ACCOUNT_MANAGER;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL; // DANGEROUS
    public static final String BATTERY_STATS = Manifest.permission.BATTERY_STATS;
    @SuppressLint("InlinedApi") // API-16+
    public static final String BIND_ACCESSIBILITY_SERVICE = Manifest.permission.BIND_ACCESSIBILITY_SERVICE;
    public static final String BIND_APPWIDGET = Manifest.permission.BIND_APPWIDGET;
    @SuppressLint("InlinedApi") // API-22+
    @Deprecated
    public static final String BIND_CARRIER_MESSAGING_SERVICE = Manifest.permission.BIND_CARRIER_MESSAGING_SERVICE;
    @SuppressLint("InlinedApi") // API-23+
    public static final String BIND_CARRIER_SERVICES = Manifest.permission.BIND_CARRIER_SERVICES;
    @SuppressLint("InlinedApi") // API-23+
    public static final String BIND_CHOOSER_TARGET_SERVICE = Manifest.permission.BIND_CHOOSER_TARGET_SERVICE;
    public static final String BIND_DEVICE_ADMIN = Manifest.permission.BIND_DEVICE_ADMIN;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BIND_DREAM_SERVICE = Manifest.permission.BIND_DREAM_SERVICE;
    @SuppressLint("InlinedApi") // API-23+
    public static final String BIND_INCALL_SERVICE = Manifest.permission.BIND_INCALL_SERVICE;
    public static final String BIND_INPUT_METHOD = Manifest.permission.BIND_INPUT_METHOD;
    @SuppressLint("InlinedApi") // API-23+
    public static final String BIND_MIDI_DEVICE_SERVICE = Manifest.permission.BIND_MIDI_DEVICE_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String BIND_NFC_SERVICE = Manifest.permission.BIND_NFC_SERVICE;
    @SuppressLint("InlinedApi") // API-18+
    public static final String BIND_NOTIFICATION_LISTENER_SERVICE = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String BIND_PRINT_SERVICE = Manifest.permission.BIND_PRINT_SERVICE;
    public static final String BIND_REMOTEVIEWS = Manifest.permission.BIND_REMOTEVIEWS;
    @SuppressLint("InlinedApi") // API-23+
    public static final String BIND_TELECOM_CONNECTION_SERVICE = Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE;
    public static final String BIND_TEXT_SERVICE = Manifest.permission.BIND_TEXT_SERVICE;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BIND_TV_INPUT = Manifest.permission.BIND_TV_INPUT;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BIND_VOICE_INTERACTION = Manifest.permission.BIND_VOICE_INTERACTION;
    public static final String BIND_VPN_SERVICE = Manifest.permission.BIND_VPN_SERVICE;
    public static final String BIND_WALLPAPER = Manifest.permission.BIND_WALLPAPER;
    public static final String BLUETOOTH = Manifest.permission.BLUETOOTH;
    public static final String BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN;
    @SuppressLint("InlinedApi") // API-19+
    public static final String BLUETOOTH_PRIVILEGED = Manifest.permission.BLUETOOTH_PRIVILEGED;
    @SuppressLint("InlinedApi") // API-20+
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS; // DANGEROUS
    public static final String BROADCAST_PACKAGE_REMOVED = Manifest.permission.BROADCAST_PACKAGE_REMOVED;
    public static final String BROADCAST_SMS = Manifest.permission.BROADCAST_SMS;
    public static final String BROADCAST_STICKY = Manifest.permission.BROADCAST_STICKY;
    public static final String BROADCAST_WAP_PUSH = Manifest.permission.BROADCAST_WAP_PUSH;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE; // DANGEROUS
    public static final String CALL_PRIVILEGED = Manifest.permission.CALL_PRIVILEGED;
    public static final String CAMERA = Manifest.permission.CAMERA; // DANGEROUS
    @SuppressLint("InlinedApi") // API-19+
    public static final String CAPTURE_AUDIO_OUTPUT = Manifest.permission.CAPTURE_AUDIO_OUTPUT;
    @SuppressLint("InlinedApi") // API-19+
    public static final String CAPTURE_SECURE_VIDEO_OUTPUT = Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT;
    @SuppressLint("InlinedApi") // API-19+
    public static final String CAPTURE_VIDEO_OUTPUT = Manifest.permission.CAPTURE_VIDEO_OUTPUT;
    public static final String CHANGE_COMPONENT_ENABLED_STATE = Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE;
    public static final String CHANGE_CONFIGURATION = Manifest.permission.CHANGE_CONFIGURATION;
    public static final String CHANGE_NETWORK_STATE = Manifest.permission.CHANGE_NETWORK_STATE;
    public static final String CHANGE_WIFI_MULTICAST_STATE = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
    public static final String CHANGE_WIFI_STATE = Manifest.permission.CHANGE_WIFI_STATE;
    public static final String CLEAR_APP_CACHE = Manifest.permission.CLEAR_APP_CACHE;
    public static final String CONTROL_LOCATION_UPDATES = Manifest.permission.CONTROL_LOCATION_UPDATES;
    public static final String DELETE_CACHE_FILES = Manifest.permission.DELETE_CACHE_FILES;
    public static final String DELETE_PACKAGES = Manifest.permission.DELETE_PACKAGES;
    public static final String DIAGNOSTIC = Manifest.permission.DIAGNOSTIC;
    public static final String DISABLE_KEYGUARD = Manifest.permission.DISABLE_KEYGUARD;
    public static final String DUMP = Manifest.permission.DUMP;
    public static final String EXPAND_STATUS_BAR = Manifest.permission.EXPAND_STATUS_BAR;
    public static final String FACTORY_TEST = Manifest.permission.FACTORY_TEST;
    public static final String FLASHLIGHT = Manifest.permission.FLASHLIGHT;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    @SuppressLint("InlinedApi") // API-23+
    public static final String GET_ACCOUNTS_PRIVILEGED = Manifest.permission.GET_ACCOUNTS_PRIVILEGED;
    public static final String GET_PACKAGE_SIZE = Manifest.permission.GET_PACKAGE_SIZE;
    @Deprecated
    public static final String GET_TASKS = Manifest.permission.GET_TASKS;
    public static final String GLOBAL_SEARCH = Manifest.permission.GLOBAL_SEARCH;
    public static final String INSTALL_LOCATION_PROVIDER = Manifest.permission.INSTALL_LOCATION_PROVIDER;
    public static final String INSTALL_PACKAGES = Manifest.permission.INSTALL_PACKAGES;
    @SuppressLint("InlinedApi") // API-19+
    public static final String INSTALL_SHORTCUT = Manifest.permission.INSTALL_SHORTCUT;
    public static final String INTERNET = Manifest.permission.INTERNET;
    public static final String KILL_BACKGROUND_PROCESSES = Manifest.permission.KILL_BACKGROUND_PROCESSES;
    @SuppressLint("InlinedApi") // API-18+
    public static final String LOCATION_HARDWARE = Manifest.permission.LOCATION_HARDWARE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String MANAGE_DOCUMENTS = Manifest.permission.MANAGE_DOCUMENTS;
    public static final String MASTER_CLEAR = Manifest.permission.MASTER_CLEAR;
    @SuppressLint("InlinedApi") // API-19+
    public static final String MEDIA_CONTENT_CONTROL = Manifest.permission.MEDIA_CONTENT_CONTROL;
    public static final String MODIFY_AUDIO_SETTINGS = Manifest.permission.MODIFY_AUDIO_SETTINGS;
    public static final String MODIFY_PHONE_STATE = Manifest.permission.MODIFY_PHONE_STATE;
    public static final String MOUNT_FORMAT_FILESYSTEMS = Manifest.permission.MOUNT_FORMAT_FILESYSTEMS;
    public static final String MOUNT_UNMOUNT_FILESYSTEMS = Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
    public static final String NFC = Manifest.permission.NFC;
    @SuppressLint("InlinedApi") // API-23+
    public static final String PACKAGE_USAGE_STATS = Manifest.permission.PACKAGE_USAGE_STATS;
    @Deprecated
    public static final String PERSISTENT_ACTIVITY = Manifest.permission.PERSISTENT_ACTIVITY;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS; // DANGEROUS
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR; // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG; // DANGEROUS
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS; // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE; // DANGEROUS
    public static final String READ_FRAME_BUFFER = Manifest.permission.READ_FRAME_BUFFER;
    @Deprecated
    public static final String READ_INPUT_STATE = Manifest.permission.READ_INPUT_STATE;
    public static final String READ_LOGS = Manifest.permission.READ_LOGS;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE; // DANGEROUS
    public static final String READ_SMS = Manifest.permission.READ_SMS; // DANGEROUS
    @SuppressLint("InlinedApi") // API-15+
    public static final String READ_SYNC_SETTINGS = Manifest.permission.READ_SYNC_SETTINGS;
    public static final String READ_SYNC_STATS = Manifest.permission.READ_SYNC_STATS;
    @SuppressLint("InlinedApi") // API-21+
    public static final String READ_VOICEMAIL = Manifest.permission.READ_VOICEMAIL;
    public static final String REBOOT = Manifest.permission.REBOOT;
    public static final String RECEIVE_BOOT_COMPLETED = Manifest.permission.RECEIVE_BOOT_COMPLETED;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS; // DANGEROUS
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS; // DANGEROUS
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH; // DANGEROUS
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO; // DANGEROUS
    public static final String REORDER_TASKS = Manifest.permission.REORDER_TASKS;
    @SuppressLint("InlinedApi") // API-23+
    public static final String REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
    @SuppressLint("InlinedApi") // API-23+
    public static final String REQUEST_INSTALL_PACKAGES = Manifest.permission.REQUEST_INSTALL_PACKAGES;
    @Deprecated
    public static final String RESTART_PACKAGES = Manifest.permission.RESTART_PACKAGES;
    @SuppressLint("InlinedApi") // API-18+
    public static final String SEND_RESPOND_VIA_MESSAGE = Manifest.permission.SEND_RESPOND_VIA_MESSAGE;
    public static final String SEND_SMS = Manifest.permission.SEND_SMS; // DANGEROUS
    public static final String SET_ALARM = Manifest.permission.SET_ALARM;
    public static final String SET_ALWAYS_FINISH = Manifest.permission.SET_ALWAYS_FINISH;
    public static final String SET_ANIMATION_SCALE = Manifest.permission.SET_ANIMATION_SCALE;
    public static final String SET_DEBUG_APP = Manifest.permission.SET_DEBUG_APP;
    @Deprecated
    public static final String SET_PREFERRED_APPLICATIONS = Manifest.permission.SET_PREFERRED_APPLICATIONS;
    public static final String SET_PROCESS_LIMIT = Manifest.permission.SET_PROCESS_LIMIT;
    public static final String SET_TIME = Manifest.permission.SET_TIME;
    public static final String SET_TIME_ZONE = Manifest.permission.SET_TIME_ZONE;
    public static final String SET_WALLPAPER = Manifest.permission.SET_WALLPAPER;
    public static final String SET_WALLPAPER_HINTS = Manifest.permission.SET_WALLPAPER_HINTS;
    public static final String SIGNAL_PERSISTENT_PROCESSES = Manifest.permission.SIGNAL_PERSISTENT_PROCESSES;
    public static final String STATUS_BAR = Manifest.permission.STATUS_BAR;
    public static final String SYSTEM_ALERT_WINDOW = Manifest.permission.SYSTEM_ALERT_WINDOW;
    @SuppressLint("InlinedApi") // API-19+
    public static final String TRANSMIT_IR = Manifest.permission.TRANSMIT_IR;
    @SuppressLint("InlinedApi") // API-19+
    public static final String UNINSTALL_SHORTCUT = Manifest.permission.UNINSTALL_SHORTCUT;
    public static final String UPDATE_DEVICE_STATS = Manifest.permission.UPDATE_DEVICE_STATS;
    @SuppressLint("InlinedApi") // API-23+
    public static final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT;
    public static final String USE_SIP = Manifest.permission.USE_SIP; // DANGEROUS
    public static final String VIBRATE = Manifest.permission.VIBRATE;
    public static final String WAKE_LOCK = Manifest.permission.WAKE_LOCK;
    public static final String WRITE_APN_SETTINGS = Manifest.permission.WRITE_APN_SETTINGS;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR; // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG; // DANGEROUS
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS; // DANGEROUS
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE; // DANGEROUS
    public static final String WRITE_GSERVICES = Manifest.permission.WRITE_GSERVICES;
    public static final String WRITE_SECURE_SETTINGS = Manifest.permission.WRITE_SECURE_SETTINGS;
    public static final String WRITE_SETTINGS = Manifest.permission.WRITE_SETTINGS;
    public static final String WRITE_SYNC_SETTINGS = Manifest.permission.WRITE_SYNC_SETTINGS;
    @SuppressLint("InlinedApi") // API-21+
    public static final String WRITE_VOICEMAIL = Manifest.permission.WRITE_VOICEMAIL;

    public static boolean permission(@NonNull final String permission) {
        if (AndroidHelper.api() < AndroidHelper.API_23) {
            final PackageManager packageManager = ApplicationHelper.context().getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return false;
            }
            return (packageManager.checkPermission(permission, ApplicationHelper.packageName()) == PackageManager.PERMISSION_GRANTED);
        }
        return (ApplicationHelper.context().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    // TODO test on API-23
    public static void request(@NonNull final SkeletonActivity skeletonActivity, @NonNull final String[] permissions) {
        if (AndroidHelper.api() < AndroidHelper.API_23) {
            LogHelper.info("No Runtime Permissions -- bridging to onRequestPermissionsResult()");
            skeletonActivity.onRequestPermissionsResult(REQUEST_CODE, permissions, new int[] { PackageManager.PERMISSION_GRANTED });
            return ;
        }
        skeletonActivity.requestPermissions(permissions, REQUEST_CODE);
    }

    public static boolean granted(@NonNull final int[] grantResults) {
        for (final int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static boolean granted(@NonNull final String permission, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (AndroidHelper.api() < AndroidHelper.API_23) {
            LogHelper.info("No Runtime Permissions -- bridging to check[Self]Permission()");
            return permission(permission);
        }
        final int index = ArrayHelper.index(permissions, permission);
        return ((index != -1) && (grantResults[index] == PackageManager.PERMISSION_GRANTED));
    }

    public static boolean revocable(@NonNull final String permission) {
        final PackageManager packageManager = ApplicationHelper.context().getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }
        try {
            final PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
            switch (permissionInfo.protectionLevel) {
                case PermissionInfo.PROTECTION_NORMAL:
                case PermissionInfo.PROTECTION_SIGNATURE:
                    // auto-accepted
                    return false;
                default:
                    // dangerous, revocable
                    return true;
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return false;
        }
    }

}
