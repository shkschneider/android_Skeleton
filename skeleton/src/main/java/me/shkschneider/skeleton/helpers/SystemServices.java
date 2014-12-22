package me.shkschneider.skeleton.helpers;

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

/**
 * @TODO
 *
 * @author Alan Schneider
 * @since 1.0
 */
@SuppressWarnings("unused")
public class SystemServices {

    private static Object service(@NonNull final String service) {
        return SkeletonApplication.CONTEXT.getSystemService(service);
    }

    public static AccessibilityManager accessibility() {
        return (AccessibilityManager) service(Context.ACCESSIBILITY_SERVICE);
    }

    public static AccountManager account() {
        return (AccountManager) service(Context.ACCOUNT_SERVICE);
    }

    public static ActivityManager activity() {
        return (ActivityManager) service(Context.ACTIVITY_SERVICE);
    }

    public static AlarmManager alarm() {
        return (AlarmManager) service(Context.ALARM_SERVICE);
    }

    public static AudioManager audio() {
        return (AudioManager) service(Context.AUDIO_SERVICE);
    }

    public static ClipboardManager clipboard() {
        return (ClipboardManager) service(Context.CLIPBOARD_SERVICE);
    }

    public static ConnectivityManager connectivity() {
        return (ConnectivityManager) service(Context.CONNECTIVITY_SERVICE);
    }

    public static DevicePolicyManager devicePolicy() {
        return (DevicePolicyManager) service(Context.DEVICE_POLICY_SERVICE);
    }

    public static DownloadManager download() {
        return (DownloadManager) service(Context.DOWNLOAD_SERVICE);
    }

    public static DropBoxManager dropBox() {
        return (DropBoxManager) service(Context.DROPBOX_SERVICE);
    }

    public static InputMethodManager inputMethod() {
        return (InputMethodManager) service(Context.INPUT_METHOD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static InputManager input() {
        return (InputManager) service(Context.INPUT_SERVICE);
    }

    public static KeyguardManager keyguard() {
        return (KeyguardManager) service(Context.KEYGUARD_SERVICE);
    }

    public static LayoutInflater layoutInflater() {
        return (LayoutInflater) service(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager location() {
        return (LocationManager) service(Context.LOCATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static MediaRouter mediaRouter() {
        return (MediaRouter) service(Context.MEDIA_ROUTER_SERVICE);
    }

    public static NfcManager nfc() {
        return (NfcManager) service(Context.NFC_SERVICE);
    }

    public static NotificationManager notification() {
        return (NotificationManager) service(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static NsdManager nsd() {
        return (NsdManager) service(Context.NSD_SERVICE);
    }

    public static PowerManager power() {
        return (PowerManager) service(Context.POWER_SERVICE);
    }

    public static SearchManager search() {
        return (SearchManager) service(Context.SEARCH_SERVICE);
    }

    public static SensorManager sensor() {
        return (SensorManager) service(Context.SENSOR_SERVICE);
    }

    public static StorageManager storage() {
        return (StorageManager) service(Context.STORAGE_SERVICE);
    }

    public static TelephonyManager telephony() {
        return (TelephonyManager) service(Context.TELEPHONY_SERVICE);
    }

    public static TextServicesManager textManager() {
        return (TextServicesManager) service(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    public static UiModeManager uiMode() {
        return (UiModeManager) service(Context.UI_MODE_SERVICE);
    }

    public static UsbManager usb() {
        return (UsbManager) service(Context.USB_SERVICE);
    }

    public static Vibrator vibrator() {
        return (Vibrator) service(Context.VIBRATOR_SERVICE);
    }

    public static WifiP2pManager wifiP2p() {
        return (WifiP2pManager) service(Context.WIFI_P2P_SERVICE);
    }

    public static WifiManager wifi() {
        return (WifiManager) service(Context.WIFI_SERVICE);
    }

    public static WindowManager window() {
        return (WindowManager) service(Context.WINDOW_SERVICE);
    }

}
