package me.shkschneider.skeleton.helper;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.midi.MidiManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.NonNull;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

// <http://developer.android.com/reference/android/content/Context.html>
public class SystemServices {

    protected SystemServices() {
        // Empty
    }

    private static Object service(@NonNull final String service) {
        return ApplicationHelper.context().getSystemService(service);
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

    @TargetApi(AndroidHelper.API_21)
    public static AppWidgetManager appWidgetManager() {
        return (AppWidgetManager) service(Context.APPWIDGET_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static AppOpsManager appOpsManager() {
        return (AppOpsManager) service(Context.APPWIDGET_SERVICE);
    }

    public static AudioManager audioManager() {
        return (AudioManager) service(Context.AUDIO_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static BatteryManager batteryManager() {
        return (BatteryManager) service(Context.BATTERY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_18)
    public static BluetoothManager bluetoothManager() {
        return (BluetoothManager) service(Context.BLUETOOTH_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static CameraManager cameraManager() {
        return (CameraManager) service(Context.CAMERA_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static CaptioningManager captioningManager() {
        return (CaptioningManager) service(Context.CAPTIONING_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static CarrierConfigManager carrierConfigManager() {
        return (CarrierConfigManager) service(Context.CARRIER_CONFIG_SERVICE);
    }

    public static ClipboardManager clipboardManager() {
        return (ClipboardManager) service(Context.CLIPBOARD_SERVICE);
    }

    public static ConnectivityManager connectivityManager() {
        return (ConnectivityManager) service(Context.CONNECTIVITY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static ConsumerIrManager consumerIrManager() {
        return (ConsumerIrManager) service(Context.CONSUMER_IR_SERVICE);
    }

    public static DevicePolicyManager devicePolicyManager() {
        return (DevicePolicyManager) service(Context.DEVICE_POLICY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_17)
    public static DisplayManager displayManager() {
        return (DisplayManager) service(Context.DISPLAY_SERVICE);
    }

    public static DownloadManager downloadManager() {
        return (DownloadManager) service(Context.DOWNLOAD_SERVICE);
    }

    public static DropBoxManager dropBoxManager() {
        return (DropBoxManager) service(Context.DROPBOX_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static FingerprintManager fingerprintManager() {
        return (FingerprintManager) service(Context.FINGERPRINT_SERVICE);
    }

    public static InputMethodManager inputMethodManager() {
        return (InputMethodManager) service(Context.INPUT_METHOD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static InputManager inputManager() {
        return (InputManager) service(Context.INPUT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static JobScheduler jobScheduler() {
        return (JobScheduler) service(Context.JOB_SCHEDULER_SERVICE);
    }

    public static KeyguardManager keyguardManager() {
        return (KeyguardManager) service(Context.KEYGUARD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static LauncherApps launcherApps() {
        return (LauncherApps) service(Context.LAUNCHER_APPS_SERVICE);
    }

    public static LayoutInflater layoutInflater() {
        return (LayoutInflater) service(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager locationManager() {
        return (LocationManager) service(Context.LOCATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static MediaProjectionManager mediaProjectionManager() {
        return (MediaProjectionManager) service(Context.MEDIA_PROJECTION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static MediaRouter mediaRouter() {
        return (MediaRouter) service(Context.MEDIA_ROUTER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static MediaSessionManager mediaSessionManager() {
        return (MediaSessionManager) service(Context.MEDIA_SESSION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static MidiManager midiManager() {
        return (MidiManager) service(Context.MIDI_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static NetworkStatsManager networkStatsManager() {
        return (NetworkStatsManager) service(Context.NETWORK_STATS_SERVICE);
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

    @TargetApi(AndroidHelper.API_19)
    public static PrintManager printManager() {
        return (PrintManager) service(Context.PRINT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static RestrictionsManager restrictionsManager() {
        return (RestrictionsManager) service(Context.RESTRICTIONS_SERVICE);
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

    @TargetApi(AndroidHelper.API_21)
    public static TelecomManager telecomManager() {
        return (TelecomManager) service(Context.TELECOM_SERVICE);
    }

    public static TelephonyManager telephonyManager() {
        return (TelephonyManager) service(Context.TELEPHONY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_22)
    public static SubscriptionManager subscriptionManager() {
        return (SubscriptionManager) service(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    public static TextServicesManager textServicesManager() {
        return (TextServicesManager) service(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static TvInputManager tvInputManager() {
        return (TvInputManager) service(Context.TV_INPUT_SERVICE);
    }

    public static UiModeManager uiModeManager() {
        return (UiModeManager) service(Context.UI_MODE_SERVICE);
    }

    @TargetApi(AndroidHelper.API_22)
    public static UsageStatsManager usageStatsManager() {
        return (UsageStatsManager) service(Context.USAGE_STATS_SERVICE);
    }

    public static UsbManager usbManager() {
        return (UsbManager) service(Context.USB_SERVICE);
    }

    @TargetApi(AndroidHelper.API_17)
    public static UserManager userManager() {
        return (UserManager) service(Context.USER_SERVICE);
    }

    public static Vibrator vibrator() {
        return (Vibrator) service(Context.VIBRATOR_SERVICE);
    }

    public static WallpaperService wallpaperService() {
        return (WallpaperService) service(Context.WALLPAPER_SERVICE);
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
