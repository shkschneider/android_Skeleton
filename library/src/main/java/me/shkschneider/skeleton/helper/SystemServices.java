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
import android.view.textservice.TextServicesManager;

// <http://developer.android.com/reference/android/content/Context.html>
public class SystemServices {

    protected SystemServices() {
        // Empty
    }

    private static Object service(@NonNull final Context context, @NonNull final String service) {
        return context.getSystemService(service);
    }

    public static AccessibilityManager accessibilityManager(@NonNull final Context context) {
        return (AccessibilityManager) service(context, Context.ACCESSIBILITY_SERVICE);
    }

    public static AccountManager accountManager(@NonNull final Context context) {
        return (AccountManager) service(context, Context.ACCOUNT_SERVICE);
    }

    public static ActivityManager activityManager(@NonNull final Context context) {
        return (ActivityManager) service(context, Context.ACTIVITY_SERVICE);
    }

    public static AlarmManager alarmManager(@NonNull final Context context) {
        return (AlarmManager) service(context, Context.ALARM_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static AppWidgetManager appWidgetManager(@NonNull final Context context) {
        return (AppWidgetManager) service(context, Context.APPWIDGET_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static AppOpsManager appOpsManager(@NonNull final Context context) {
        return (AppOpsManager) service(context, Context.APPWIDGET_SERVICE);
    }

    public static AudioManager audioManager(@NonNull final Context context) {
        return (AudioManager) service(context, Context.AUDIO_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static BatteryManager batteryManager(@NonNull final Context context) {
        return (BatteryManager) service(context, Context.BATTERY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_18)
    public static BluetoothManager bluetoothManager(@NonNull final Context context) {
        return (BluetoothManager) service(context, Context.BLUETOOTH_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static CameraManager cameraManager(@NonNull final Context context) {
        return (CameraManager) service(context, Context.CAMERA_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static CaptioningManager captioningManager(@NonNull final Context context) {
        return (CaptioningManager) service(context, Context.CAPTIONING_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static CarrierConfigManager carrierConfigManager(@NonNull final Context context) {
        return (CarrierConfigManager) service(context, Context.CARRIER_CONFIG_SERVICE);
    }

    public static ClipboardManager clipboardManager(@NonNull final Context context) {
        return (ClipboardManager) service(context, Context.CLIPBOARD_SERVICE);
    }

    public static ConnectivityManager connectivityManager(@NonNull final Context context) {
        return (ConnectivityManager) service(context, Context.CONNECTIVITY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static ConsumerIrManager consumerIrManager(@NonNull final Context context) {
        return (ConsumerIrManager) service(context, Context.CONSUMER_IR_SERVICE);
    }

    public static DevicePolicyManager devicePolicyManager(@NonNull final Context context) {
        return (DevicePolicyManager) service(context, Context.DEVICE_POLICY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_17)
    public static DisplayManager displayManager(@NonNull final Context context) {
        return (DisplayManager) service(context, Context.DISPLAY_SERVICE);
    }

    public static DownloadManager downloadManager(@NonNull final Context context) {
        return (DownloadManager) service(context, Context.DOWNLOAD_SERVICE);
    }

    public static DropBoxManager dropBoxManager(@NonNull final Context context) {
        return (DropBoxManager) service(context, Context.DROPBOX_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static FingerprintManager fingerprintManager(@NonNull final Context context) {
        return (FingerprintManager) service(context, Context.FINGERPRINT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_24)
    public static HardwarePropertiesManager hardwarePropertiesService(@NonNull final Context context) {
        return (HardwarePropertiesManager) service(context, Context.HARDWARE_PROPERTIES_SERVICE);
    }

    public static InputMethodManager inputMethodManager(@NonNull final Context context) {
        return (InputMethodManager) service(context, Context.INPUT_METHOD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static InputManager inputManager(@NonNull final Context context) {
        return (InputManager) service(context, Context.INPUT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static JobScheduler jobScheduler(@NonNull final Context context) {
        return (JobScheduler) service(context, Context.JOB_SCHEDULER_SERVICE);
    }

    public static KeyguardManager keyguardManager(@NonNull final Context context) {
        return (KeyguardManager) service(context, Context.KEYGUARD_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static LauncherApps launcherApps(@NonNull final Context context) {
        return (LauncherApps) service(context, Context.LAUNCHER_APPS_SERVICE);
    }

    public static LayoutInflater layoutInflater(@NonNull final Context context) {
        return (LayoutInflater) service(context, Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager locationManager(@NonNull final Context context) {
        return (LocationManager) service(context, Context.LOCATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static MediaProjectionManager mediaProjectionManager(@NonNull final Context context) {
        return (MediaProjectionManager) service(context, Context.MEDIA_PROJECTION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static MediaRouter mediaRouter(@NonNull final Context context) {
        return (MediaRouter) service(context, Context.MEDIA_ROUTER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static MediaSessionManager mediaSessionManager(@NonNull final Context context) {
        return (MediaSessionManager) service(context, Context.MEDIA_SESSION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static MidiManager midiManager(@NonNull final Context context) {
        return (MidiManager) service(context, Context.MIDI_SERVICE);
    }

    @TargetApi(AndroidHelper.API_23)
    public static NetworkStatsManager networkStatsManager(@NonNull final Context context) {
        return (NetworkStatsManager) service(context, Context.NETWORK_STATS_SERVICE);
    }

    public static NfcManager nfcManager(@NonNull final Context context) {
        return (NfcManager) service(context, Context.NFC_SERVICE);
    }

    public static NotificationManager notificationManager(@NonNull final Context context) {
        return (NotificationManager) service(context, Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(AndroidHelper.API_16)
    public static NsdManager nsdManager(@NonNull final Context context) {
        return (NsdManager) service(context, Context.NSD_SERVICE);
    }

    public static PowerManager powerManager(@NonNull final Context context) {
        return (PowerManager) service(context, Context.POWER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_19)
    public static PrintManager printManager(@NonNull final Context context) {
        return (PrintManager) service(context, Context.PRINT_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static RestrictionsManager restrictionsManager(@NonNull final Context context) {
        return (RestrictionsManager) service(context, Context.RESTRICTIONS_SERVICE);
    }

    public static SearchManager searchManager(@NonNull final Context context) {
        return (SearchManager) service(context, Context.SEARCH_SERVICE);
    }

    public static SensorManager sensorManager(@NonNull final Context context) {
        return (SensorManager) service(context, Context.SENSOR_SERVICE);
    }

    @TargetApi(AndroidHelper.API_25)
    public static ShortcutManager shortcutManager(@NonNull final Context context) {
        return (ShortcutManager) service(context, Context.SHORTCUT_SERVICE);
    }

    public static StorageManager storageManager(@NonNull final Context context) {
        return (StorageManager) service(context, Context.STORAGE_SERVICE);
    }

    @TargetApi(AndroidHelper.API_24)
    public static SystemHealthManager systemHealthService(@NonNull final Context context) {
        return (SystemHealthManager) service(context, Context.SYSTEM_HEALTH_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static TelecomManager telecomManager(@NonNull final Context context) {
        return (TelecomManager) service(context, Context.TELECOM_SERVICE);
    }

    public static TelephonyManager telephonyManager(@NonNull final Context context) {
        return (TelephonyManager) service(context, Context.TELEPHONY_SERVICE);
    }

    @TargetApi(AndroidHelper.API_22)
    public static SubscriptionManager subscriptionManager(@NonNull final Context context) {
        return (SubscriptionManager) service(context, Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    public static TextServicesManager textServicesManager(@NonNull final Context context) {
        return (TextServicesManager) service(context, Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    @TargetApi(AndroidHelper.API_21)
    public static TvInputManager tvInputManager(@NonNull final Context context) {
        return (TvInputManager) service(context, Context.TV_INPUT_SERVICE);
    }

    public static UiModeManager uiModeManager(@NonNull final Context context) {
        return (UiModeManager) service(context, Context.UI_MODE_SERVICE);
    }

    @TargetApi(AndroidHelper.API_22)
    public static UsageStatsManager usageStatsManager(@NonNull final Context context) {
        return (UsageStatsManager) service(context, Context.USAGE_STATS_SERVICE);
    }

    public static UsbManager usbManager(@NonNull final Context context) {
        return (UsbManager) service(context, Context.USB_SERVICE);
    }

    @TargetApi(AndroidHelper.API_17)
    public static UserManager userManager(@NonNull final Context context) {
        return (UserManager) service(context, Context.USER_SERVICE);
    }

    public static Vibrator vibrator(@NonNull final Context context) {
        return (Vibrator) service(context, Context.VIBRATOR_SERVICE);
    }

    public static WallpaperService wallpaperService(@NonNull final Context context) {
        return (WallpaperService) service(context, Context.WALLPAPER_SERVICE);
    }

    public static WifiP2pManager wifiP2pManager(@NonNull final Context context) {
        return (WifiP2pManager) service(context, Context.WIFI_P2P_SERVICE);
    }

    public static WifiManager wifiManager(@NonNull final Context context) {
        return (WifiManager) service(context, Context.WIFI_SERVICE);
    }

    public static WindowManager windowManager(@NonNull final Context context) {
        return (WindowManager) service(context, Context.WINDOW_SERVICE);
    }

}
