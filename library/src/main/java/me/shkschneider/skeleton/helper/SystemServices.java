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
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.companion.CompanionDeviceManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutManager;
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
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.DropBoxManager;
import android.os.HardwarePropertiesManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.health.SystemHealthManager;
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
import android.view.textclassifier.TextClassificationManager;
import android.view.textservice.TextServicesManager;

// <http://developer.android.com/reference/android/content/Context.html>
public class SystemServices {

    protected SystemServices() {
        // Empty
    }

    private static Object get(@NonNull final String service) {
        return ContextHelper.applicationContext().getSystemService(service);
    }

    @TargetApi(AndroidHelper.API_23)
    private static Object get(@NonNull final Class<?> service) {
        return ContextHelper.applicationContext().getSystemService(service);
    }

    public static AccessibilityManager accessibilityManager() {
        return (AccessibilityManager) get(Context.ACCESSIBILITY_SERVICE);
    }

    public static AccountManager accountManager() {
        return (AccountManager) get(Context.ACCOUNT_SERVICE);
    }

    public static ActivityManager activityManager() {
        return (ActivityManager) get(Context.ACTIVITY_SERVICE);
    }

    public static AlarmManager alarmManager() {
        return (AlarmManager) get(Context.ALARM_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static AppWidgetManager appWidgetManager() {
        return (AppWidgetManager) get(Context.APPWIDGET_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static AppOpsManager appOpsManager() {
        return (AppOpsManager) get(Context.APPWIDGET_SERVICE);
    }

    public static AudioManager audioManager() {
        return (AudioManager) get(Context.AUDIO_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static BatteryManager batteryManager() {
        return (BatteryManager) get(Context.BATTERY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_18)
    public static BluetoothManager bluetoothManager() {
        return (BluetoothManager) get(Context.BLUETOOTH_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static CameraManager cameraManager() {
        return (CameraManager) get(Context.CAMERA_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static CaptioningManager captioningManager() {
        return (CaptioningManager) get(Context.CAPTIONING_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static CarrierConfigManager carrierConfigManager() {
        return (CarrierConfigManager) get(Context.CARRIER_CONFIG_SERVICE);
    }

    public static ClipboardManager clipboardManager() {
        return (ClipboardManager) get(Context.CLIPBOARD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_26)
    public static CompanionDeviceManager companionDeviceManager() {
        return (CompanionDeviceManager) get(Context.COMPANION_DEVICE_SERVICE);
    }

    public static ConnectivityManager connectivityManager() {
        return (ConnectivityManager) get(Context.CONNECTIVITY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static ConsumerIrManager consumerIrManager() {
        return (ConsumerIrManager) get(Context.CONSUMER_IR_SERVICE);
    }

    public static DevicePolicyManager devicePolicyManager() {
        return (DevicePolicyManager) get(Context.DEVICE_POLICY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_17)
    public static DisplayManager displayManager() {
        return (DisplayManager) get(Context.DISPLAY_SERVICE);
    }

    public static DownloadManager downloadManager() {
        return (DownloadManager) get(Context.DOWNLOAD_SERVICE);
    }

    public static DropBoxManager dropBoxManager() {
        return (DropBoxManager) get(Context.DROPBOX_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static FingerprintManager fingerprintManager() {
        return (FingerprintManager) get(Context.FINGERPRINT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_24)
    public static HardwarePropertiesManager hardwarePropertiesService() {
        return (HardwarePropertiesManager) get(Context.HARDWARE_PROPERTIES_SERVICE);
    }

    public static InputMethodManager inputMethodManager() {
        return (InputMethodManager) get(Context.INPUT_METHOD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static InputManager inputManager() {
        return (InputManager) get(Context.INPUT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static JobScheduler jobScheduler() {
        return (JobScheduler) get(Context.JOB_SCHEDULER_SERVICE);
    }

    public static KeyguardManager keyguardManager() {
        return (KeyguardManager) get(Context.KEYGUARD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static LauncherApps launcherApps() {
        return (LauncherApps) get(Context.LAUNCHER_APPS_SERVICE);
    }

    public static LayoutInflater layoutInflater() {
        return (LayoutInflater) get(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager locationManager() {
        return (LocationManager) get(Context.LOCATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static MediaProjectionManager mediaProjectionManager() {
        return (MediaProjectionManager) get(Context.MEDIA_PROJECTION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static MediaRouter mediaRouter() {
        return (MediaRouter) get(Context.MEDIA_ROUTER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static MediaSessionManager mediaSessionManager() {
        return (MediaSessionManager) get(Context.MEDIA_SESSION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static MidiManager midiManager() {
        return (MidiManager) get(Context.MIDI_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static NetworkStatsManager networkStatsManager() {
        return (NetworkStatsManager) get(Context.NETWORK_STATS_SERVICE);
    }

    public static NfcManager nfcManager() {
        return (NfcManager) get(Context.NFC_SERVICE);
    }

    public static NotificationManager notificationManager() {
        return (NotificationManager) get(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static NsdManager nsdManager() {
        return (NsdManager) get(Context.NSD_SERVICE);
    }

    public static PowerManager powerManager() {
        return (PowerManager) get(Context.POWER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static PrintManager printManager() {
        return (PrintManager) get(Context.PRINT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static RestrictionsManager restrictionsManager() {
        return (RestrictionsManager) get(Context.RESTRICTIONS_SERVICE);
    }

    public static SearchManager searchManager() {
        return (SearchManager) get(Context.SEARCH_SERVICE);
    }

    public static SensorManager sensorManager() {
        return (SensorManager) get(Context.SENSOR_SERVICE);
    }

    @TargetApi(AndroidHelper.API_25)
    public static ShortcutManager shortcutManager() {
        return (ShortcutManager) get(Context.SHORTCUT_SERVICE);
    }

    public static StorageManager storageManager() {
        return (StorageManager) get(Context.STORAGE_SERVICE);
    }

    @TargetApi(AndroidHelper.API_26)
    public static StorageStatsManager storageStatsManager() {
        return (StorageStatsManager) get(Context.STORAGE_STATS_SERVICE);
    }

    @TargetApi(AndroidHelper.API_24)
    public static SystemHealthManager systemHealthService() {
        return (SystemHealthManager) get(Context.SYSTEM_HEALTH_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static TelecomManager telecomManager() {
        return (TelecomManager) get(Context.TELECOM_SERVICE);
    }

    public static TelephonyManager telephonyManager() {
        return (TelephonyManager) get(Context.TELEPHONY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_22)
    public static SubscriptionManager subscriptionManager() {
        return (SubscriptionManager) get(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_26)
    public static TextClassificationManager textClassificationManager() {
        return (TextClassificationManager) get(Context.TEXT_CLASSIFICATION_SERVICE);
    }

    public static TextServicesManager textServicesManager() {
        return (TextServicesManager) get(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static TvInputManager tvInputManager() {
        return (TvInputManager) get(Context.TV_INPUT_SERVICE);
    }

    public static UiModeManager uiModeManager() {
        return (UiModeManager) get(Context.UI_MODE_SERVICE);
    }

    @TargetApi(AndroidHelper.API_22)
    public static UsageStatsManager usageStatsManager() {
        return (UsageStatsManager) get(Context.USAGE_STATS_SERVICE);
    }

    public static UsbManager usbManager() {
        return (UsbManager) get(Context.USB_SERVICE);
    }

    @TargetApi(AndroidHelper.API_17)
    public static UserManager userManager() {
        return (UserManager) get(Context.USER_SERVICE);
    }

    public static Vibrator vibrator() {
        return (Vibrator) get(Context.VIBRATOR_SERVICE);
    }

    public static WallpaperService wallpaperService() {
        return (WallpaperService) get(Context.WALLPAPER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_26)
    public static WifiAwareManager wifiAwareManager() {
        return (WifiAwareManager) get(Context.WIFI_AWARE_SERVICE);
    }

    public static WifiP2pManager wifiP2pManager() {
        return (WifiP2pManager) get(Context.WIFI_P2P_SERVICE);
    }

    public static WifiManager wifiManager() {
        return (WifiManager) get(Context.WIFI_SERVICE);
    }

    public static WindowManager windowManager() {
        return (WindowManager) get(Context.WINDOW_SERVICE);
    }

}
