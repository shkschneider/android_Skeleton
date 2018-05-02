package me.shkschneider.skeleton.helper

import android.Manifest
import android.annotation.SuppressLint

// <http://developer.android.com/reference/android/Manifest.permission.html>
object PermissionsHelper {

    // Useless: only used to keep track of new features
    val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION // DANGEROUS
    val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION // DANGEROUS
    val ACCESS_LOCATION_EXTRA_COMMANDS = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
    val ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE
    @SuppressLint("InlinedApi") // API-23+
    val ACCESS_NOTIFICATION_POLICY = Manifest.permission.ACCESS_NOTIFICATION_POLICY
    val ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE
    val ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL // DANGEROUS
    @SuppressLint("InlinedApi") // API-26+
    val ANSWER_PHONE_CALLS = Manifest.permission.ANSWER_PHONE_CALLS // DANGEROUS
    val BLUETOOTH = Manifest.permission.BLUETOOTH
    val BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
    @SuppressLint("InlinedApi") // API-20+
    val BODY_SENSORS = Manifest.permission.BODY_SENSORS // DANGEROUS
    val BROADCAST_STICKY = Manifest.permission.BROADCAST_STICKY
    val CALL_PHONE = Manifest.permission.CALL_PHONE // DANGEROUS
    val CAMERA = Manifest.permission.CAMERA // DANGEROUS
    val CHANGE_NETWORK_STATE = Manifest.permission.CHANGE_NETWORK_STATE
    val CHANGE_WIFI_MULTICAST_STATE = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    val CHANGE_WIFI_STATE = Manifest.permission.CHANGE_WIFI_STATE
    val DISABLE_KEYGUARD = Manifest.permission.DISABLE_KEYGUARD
    val EXPAND_STATUS_BAR = Manifest.permission.EXPAND_STATUS_BAR
    val GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS // DANGEROUS
    @SuppressLint("InlinedApi") // API-23+
    val GET_ACCOUNTS_PRIVILEGED = Manifest.permission.GET_ACCOUNTS_PRIVILEGED
    val GET_PACKAGE_SIZE = Manifest.permission.GET_PACKAGE_SIZE
    @SuppressLint("InlinedApi") // API-19+
    val INSTALL_SHORTCUT = Manifest.permission.INSTALL_SHORTCUT
    @SuppressLint("InlinedApi") // API-26+
    val INSTANT_APP_FOREGROUND_SERVICE = Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE
    val INTERNET = Manifest.permission.INTERNET
    val KILL_BACKGROUND_PROCESSES = Manifest.permission.KILL_BACKGROUND_PROCESSES
    @SuppressLint("InlinedApi") // API-26+
    val MANAGE_OWN_CALLS = Manifest.permission.MANAGE_OWN_CALLS
    val MODIFY_AUDIO_SETTINGS = Manifest.permission.MODIFY_AUDIO_SETTINGS
    val NFC = Manifest.permission.NFC
    @SuppressLint("InlinedApi") // API-23+
    val PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS // DANGEROUS
    val READ_CALENDAR = Manifest.permission.READ_CALENDAR // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG // DANGEROUS
    val READ_CONTACTS = Manifest.permission.READ_CONTACTS // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE // DANGEROUS
    @SuppressLint("InlinedApi") // API-26+
    val READ_PHONE_NUMBERS = Manifest.permission.READ_PHONE_NUMBERS // DANGEROUS
    val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE // DANGEROUS
    val READ_SMS = Manifest.permission.READ_SMS // DANGEROUS
    @SuppressLint("InlinedApi") // API-15+
    val READ_SYNC_SETTINGS = Manifest.permission.READ_SYNC_SETTINGS
    val READ_SYNC_STATS = Manifest.permission.READ_SYNC_STATS
    val RECEIVE_BOOT_COMPLETED = Manifest.permission.RECEIVE_BOOT_COMPLETED
    val RECEIVE_MMS = Manifest.permission.RECEIVE_MMS // DANGEROUS
    val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS // DANGEROUS
    val RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH // DANGEROUS
    val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO // DANGEROUS
    val REORDER_TASKS = Manifest.permission.REORDER_TASKS
    @SuppressLint("InlinedApi") // API-26+
    val REQUEST_COMPANION_RUN_IN_BACKGROUND = Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND
    @SuppressLint("InlinedApi") // API-26+
    val REQUEST_COMPANION_USE_DATA_IN_BACKGROUND = Manifest.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND
    @SuppressLint("InlinedApi") // API-26+
    val REQUEST_DELETE_PACKAGES = Manifest.permission.REQUEST_DELETE_PACKAGES
    @SuppressLint("InlinedApi") // API-23+
    val REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
    @SuppressLint("InlinedApi") // API-23+
    val REQUEST_INSTALL_PACKAGES = Manifest.permission.REQUEST_INSTALL_PACKAGES
    val SEND_SMS = Manifest.permission.SEND_SMS // DANGEROUS
    val SET_ALARM = Manifest.permission.SET_ALARM
    val SET_TIME_ZONE = Manifest.permission.SET_TIME_ZONE
    val SET_WALLPAPER = Manifest.permission.SET_WALLPAPER
    val SET_WALLPAPER_HINTS = Manifest.permission.SET_WALLPAPER_HINTS
    @SuppressLint("InlinedApi") // API-19+
    val TRANSMIT_IR = Manifest.permission.TRANSMIT_IR
    @SuppressLint("InlinedApi") // API-19+
    val UNINSTALL_SHORTCUT = Manifest.permission.UNINSTALL_SHORTCUT
    @SuppressLint("InlinedApi") // API-23+
    val USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT
    val USE_SIP = Manifest.permission.USE_SIP // DANGEROUS
    val VIBRATE = Manifest.permission.VIBRATE
    val WAKE_LOCK = Manifest.permission.WAKE_LOCK
    val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR // DANGEROUS
    @SuppressLint("InlinedApi") // API-16+
    val WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG // DANGEROUS
    val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS // DANGEROUS
    val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE // DANGEROUS
    val WRITE_SYNC_SETTINGS = Manifest.permission.WRITE_SYNC_SETTINGS

}