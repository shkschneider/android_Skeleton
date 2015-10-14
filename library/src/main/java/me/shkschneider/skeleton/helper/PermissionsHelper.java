package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import me.shkschneider.runtimepermissionscompat.RuntimePermissionsCompat;

// <http://developer.android.com/reference/android/Manifest.permission.html>
public class PermissionsHelper {

    private static final int REQUEST_CODE = 23;

    protected PermissionsHelper() {
        // Empty
    }

    // Useless: only used to keep track of new features
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION; // DANGEROUS
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION; // DANGEROUS
    public static final String ACCESS_LOCATION_EXTRA_COMMANDS = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
    public static final String ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    @SuppressLint("InlinedApi") // API-23+
    public static final String ACCESS_NOTIFICATION_POLICY = Manifest.permission.ACCESS_NOTIFICATION_POLICY;
    public static final String ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL; // DANGEROUS
    public static final String BLUETOOTH = Manifest.permission.BLUETOOTH;
    public static final String BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN;
    @SuppressLint("InlinedApi") // API-20+
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS; // DANGEROUS
    public static final String BROADCAST_STICKY = Manifest.permission.BROADCAST_STICKY;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE; // DANGEROUS
    public static final String CAMERA = Manifest.permission.CAMERA; // DANGEROUS
    public static final String CHANGE_NETWORK_STATE = Manifest.permission.CHANGE_NETWORK_STATE; // FIXME
    public static final String CHANGE_WIFI_MULTICAST_STATE = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
    public static final String CHANGE_WIFI_STATE = Manifest.permission.CHANGE_WIFI_STATE;
    public static final String DISABLE_KEYGUARD = Manifest.permission.DISABLE_KEYGUARD;
    public static final String EXPAND_STATUS_BAR = Manifest.permission.EXPAND_STATUS_BAR;
    public static final String FLASHLIGHT = Manifest.permission.FLASHLIGHT;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS; // FIXME
    @SuppressLint("InlinedApi") // API-23+
    public static final String GET_ACCOUNTS_PRIVILEGED = Manifest.permission.GET_ACCOUNTS_PRIVILEGED;
    public static final String GET_PACKAGE_SIZE = Manifest.permission.GET_PACKAGE_SIZE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String INSTALL_SHORTCUT = Manifest.permission.INSTALL_SHORTCUT;
    public static final String INTERNET = Manifest.permission.INTERNET;
    public static final String KILL_BACKGROUND_PROCESSES = Manifest.permission.KILL_BACKGROUND_PROCESSES;
    public static final String MODIFY_AUDIO_SETTINGS = Manifest.permission.MODIFY_AUDIO_SETTINGS;
    public static final String NFC = Manifest.permission.NFC;
    @SuppressLint("InlinedApi") // API-23+
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS; // DANGEROUS
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR; // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG; // DANGEROUS
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS; // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE; // DANGEROUS
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE; // DANGEROUS
    public static final String READ_SMS = Manifest.permission.READ_SMS; // DANGEROUS
    @SuppressLint("InlinedApi") // API-15+
    public static final String READ_SYNC_SETTINGS = Manifest.permission.READ_SYNC_SETTINGS;
    public static final String READ_SYNC_STATS = Manifest.permission.READ_SYNC_STATS;
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
    public static final String SEND_SMS = Manifest.permission.SEND_SMS; // DANGEROUS
    public static final String SET_ALARM = Manifest.permission.SET_ALARM;
    public static final String SET_TIME_ZONE = Manifest.permission.SET_TIME_ZONE;
    public static final String SET_WALLPAPER = Manifest.permission.SET_WALLPAPER;
    public static final String SET_WALLPAPER_HINTS = Manifest.permission.SET_WALLPAPER_HINTS;
    @SuppressLint("InlinedApi") // API-19+
    public static final String TRANSMIT_IR = Manifest.permission.TRANSMIT_IR;
    @SuppressLint("InlinedApi") // API-19+
    public static final String UNINSTALL_SHORTCUT = Manifest.permission.UNINSTALL_SHORTCUT;
    @SuppressLint("InlinedApi") // API-23+
    public static final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT;
    public static final String USE_SIP = Manifest.permission.USE_SIP; // DANGEROUS
    public static final String VIBRATE = Manifest.permission.VIBRATE;
    public static final String WAKE_LOCK = Manifest.permission.WAKE_LOCK;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR; // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG; // DANGEROUS
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS; // DANGEROUS
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE; // DANGEROUS
    public static final String WRITE_SYNC_SETTINGS = Manifest.permission.WRITE_SYNC_SETTINGS;

    @Deprecated
    public static boolean permission(@NonNull final String permission) {
        return RuntimePermissionsCompat.isGranted(ApplicationHelper.context(), permission);
    }

}
