package me.shkschneider.skeleton.helper;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.admin.DevicePolicyManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import me.shkschneider.skeleton.SkeletonApplication;

public class SystemServices {

    private static Object service(@NonNull final String service) {
        return SkeletonApplication.CONTEXT.getSystemService(service);
    }

    public static AccessibilityManager accessibilityManager() {
        return (AccessibilityManager) service(Context.ACCESSIBILITY_SERVICE);
    }

    public static AccountManager accountManager() {
        return (AccountManager) service(Context.ACCOUNT_SERVICE);
    }

    public static ActivityManager activityManager() {
        return (ActivityManager) service(Context.ACTIVITY_SERVICE);
    }

    public static AlarmManager alarmManager() {
        return (AlarmManager) service(Context.ALARM_SERVICE);
    }

    public static AudioManager audioManager() {
        return (AudioManager) service(Context.AUDIO_SERVICE);
    }

    public static ClipboardManager clipboardManager() {
        return (ClipboardManager) service(Context.CLIPBOARD_SERVICE);
    }

    public static ConnectivityManager connectivityManager() {
        return (ConnectivityManager) service(Context.CONNECTIVITY_SERVICE);
    }

    public static DevicePolicyManager devicePolicyManager() {
        return (DevicePolicyManager) service(Context.DEVICE_POLICY_SERVICE);
    }

    public static DownloadManager downloadManager() {
        return (DownloadManager) service(Context.DOWNLOAD_SERVICE);
    }

    public static DropBoxManager dropBoxManager() {
        return (DropBoxManager) service(Context.DROPBOX_SERVICE);
    }

    public static InputMethodManager inputMethodManager() {
        return (InputMethodManager) service(Context.INPUT_METHOD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static InputManager inputManager() {
        return (InputManager) service(Context.INPUT_SERVICE);
    }

    public static KeyguardManager keyguardManager() {
        return (KeyguardManager) service(Context.KEYGUARD_SERVICE);
    }

    public static LayoutInflater layoutInflater() {
        return (LayoutInflater) service(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager locationManager() {
        return (LocationManager) service(Context.LOCATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static MediaRouter mediaRouter() {
        return (MediaRouter) service(Context.MEDIA_ROUTER_SERVICE);
    }

    public static NfcManager nfcManager() {
        return (NfcManager) service(Context.NFC_SERVICE);
    }

    public static NotificationManager notificationManager() {
        return (NotificationManager) service(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static NsdManager nsdManager() {
        return (NsdManager) service(Context.NSD_SERVICE);
    }

    public static PowerManager powerManager() {
        return (PowerManager) service(Context.POWER_SERVICE);
    }

    public static SearchManager searchManager() {
        return (SearchManager) service(Context.SEARCH_SERVICE);
    }

    public static SensorManager sensorManager() {
        return (SensorManager) service(Context.SENSOR_SERVICE);
    }

    public static StorageManager storageManager() {
        return (StorageManager) service(Context.STORAGE_SERVICE);
    }

    public static TelephonyManager telephonyManager() {
        return (TelephonyManager) service(Context.TELEPHONY_SERVICE);
    }

    public static TextServicesManager textServicesManager() {
        return (TextServicesManager) service(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    public static UiModeManager uiModeManager() {
        return (UiModeManager) service(Context.UI_MODE_SERVICE);
    }

    public static UsbManager usbManager() {
        return (UsbManager) service(Context.USB_SERVICE);
    }

    public static Vibrator vibrator() {
        return (Vibrator) service(Context.VIBRATOR_SERVICE);
    }

    public static WifiP2pManager wifiP2pManager() {
        return (WifiP2pManager) service(Context.WIFI_P2P_SERVICE);
    }

    public static WifiManager wifiManager() {
        return (WifiManager) service(Context.WIFI_SERVICE);
    }

    public static WindowManager windowManager() {
        return (WindowManager) service(Context.WINDOW_SERVICE);
    }

}
