package me.shkschneider.skeleton.helperx

import android.accounts.AccountManager
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.AppOpsManager
import android.app.DownloadManager
import android.app.KeyguardManager
import android.app.NotificationManager
import android.app.SearchManager
import android.app.UiModeManager
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.companion.CompanionDeviceManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.aware.WifiAwareManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.BatteryManager
import android.os.DropBoxManager
import android.os.HardwarePropertiesManager
import android.os.PowerManager
import android.os.UserManager
import android.os.Vibrator
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.print.PrintManager
import android.service.wallpaper.WallpaperService
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textclassifier.TextClassificationManager
import android.view.textservice.TextServicesManager
import androidx.annotation.RequiresApi
import me.shkschneider.skeleton.extensions.getSystemService
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ContextHelper
import kotlin.reflect.KClass

// <http://developer.android.com/reference/android/content/Context.html>
object SystemServices {

    private fun get(service: String): Any? {
        return ContextHelper.applicationContext().getSystemService(service)
    }

    @RequiresApi(AndroidHelper.API_23)
    private fun <T: Any> get(klass: KClass<T>): T? {
        return ContextHelper.applicationContext().getSystemService(klass)
    }

    fun accessibilityManager(): AccessibilityManager? {
        return get(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?
    }

    fun accountManager(): AccountManager? {
        return get(Context.ACCOUNT_SERVICE) as AccountManager?
    }

    fun activityManager(): ActivityManager? {
        return get(Context.ACTIVITY_SERVICE) as ActivityManager?
    }

    fun alarmManager(): AlarmManager? {
        return get(Context.ALARM_SERVICE) as AlarmManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun appWidgetManager(): AppWidgetManager? {
        return get(Context.APPWIDGET_SERVICE) as AppWidgetManager?
    }

    @RequiresApi(AndroidHelper.API_19)
    fun appOpsManager(): AppOpsManager? {
        return get(Context.APP_OPS_SERVICE) as AppOpsManager?
    }

    fun audioManager(): AudioManager? {
        return get(Context.AUDIO_SERVICE) as AudioManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun batteryManager(): BatteryManager? {
        return get(Context.BATTERY_SERVICE) as BatteryManager?
    }

    @RequiresApi(AndroidHelper.API_18)
    fun bluetoothManager(): BluetoothManager? {
        return get(Context.BLUETOOTH_SERVICE) as BluetoothManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun cameraManager(): CameraManager? {
        return get(Context.CAMERA_SERVICE) as CameraManager?
    }

    @RequiresApi(AndroidHelper.API_19)
    fun captioningManager(): CaptioningManager? {
        return get(Context.CAPTIONING_SERVICE) as CaptioningManager?
    }

    @RequiresApi(AndroidHelper.API_23)
    fun carrierConfigManager(): CarrierConfigManager? {
        return get(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager?
    }

    fun clipboardManager(): ClipboardManager? {
        return get(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    }

    @RequiresApi(AndroidHelper.API_26)
    fun companionDeviceManager(): CompanionDeviceManager? {
        return get(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager?
    }

    fun connectivityManager(): ConnectivityManager? {
        return get(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }

    @RequiresApi(AndroidHelper.API_19)
    fun consumerIrManager(): ConsumerIrManager? {
        return get(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?
    }

    fun devicePolicyManager(): DevicePolicyManager? {
        return get(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
    }

    @RequiresApi(AndroidHelper.API_17)
    fun displayManager(): DisplayManager? {
        return get(Context.DISPLAY_SERVICE) as DisplayManager?
    }

    fun downloadManager(): DownloadManager? {
        return get(Context.DOWNLOAD_SERVICE) as DownloadManager?
    }

    fun dropBoxManager(): DropBoxManager? {
        return get(Context.DROPBOX_SERVICE) as DropBoxManager?
    }

    fun fingerprintManager(context: Context): FingerprintManagerCompat {
        return FingerprintManagerCompat.from(context)
    }

    @RequiresApi(AndroidHelper.API_24)
    fun hardwarePropertiesService(): HardwarePropertiesManager? {
        return get(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager?
    }

    fun inputMethodManager(): InputMethodManager? {
        return get(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    }

    @RequiresApi(AndroidHelper.API_16)
    fun inputManager(): InputManager? {
        return get(Context.INPUT_SERVICE) as InputManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun jobScheduler(): JobScheduler? {
        return get(Context.JOB_SCHEDULER_SERVICE) as JobScheduler?
    }

    fun keyguardManager(): KeyguardManager? {
        return get(Context.KEYGUARD_SERVICE) as KeyguardManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun launcherApps(): LauncherApps? {
        return get(Context.LAUNCHER_APPS_SERVICE) as LauncherApps?
    }

    fun layoutInflater(): LayoutInflater? {
        return get(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    fun locationManager(): LocationManager? {
        return get(Context.LOCATION_SERVICE) as LocationManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun mediaProjectionManager(): MediaProjectionManager? {
        return get(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager?
    }

    @RequiresApi(AndroidHelper.API_16)
    fun mediaRouter(): MediaRouter? {
        return get(Context.MEDIA_ROUTER_SERVICE) as MediaRouter?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun mediaSessionManager(): MediaSessionManager? {
        return get(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager?
    }

    @RequiresApi(AndroidHelper.API_23)
    fun midiManager(): MidiManager? {
        return get(Context.MIDI_SERVICE) as MidiManager?
    }

    @RequiresApi(AndroidHelper.API_23)
    fun networkStatsManager(): NetworkStatsManager? {
        return get(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager?
    }

    fun nfcManager(): NfcManager? {
        return get(Context.NFC_SERVICE) as NfcManager?
    }

    fun notificationManager(): NotificationManager? {
        return get(Context.NOTIFICATION_SERVICE) as NotificationManager?
    }

    @RequiresApi(AndroidHelper.API_16)
    fun nsdManager(): NsdManager? {
        return get(Context.NSD_SERVICE) as NsdManager?
    }

    fun powerManager(): PowerManager? {
        return get(Context.POWER_SERVICE) as PowerManager?
    }

    @RequiresApi(AndroidHelper.API_19)
    fun printManager(): PrintManager? {
        return get(Context.PRINT_SERVICE) as PrintManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun restrictionsManager(): RestrictionsManager? {
        return get(Context.RESTRICTIONS_SERVICE) as RestrictionsManager?
    }

    fun searchManager(): SearchManager? {
        return get(Context.SEARCH_SERVICE) as SearchManager?
    }

    fun sensorManager(): SensorManager? {
        return get(Context.SENSOR_SERVICE) as SensorManager?
    }

    @RequiresApi(AndroidHelper.API_25)
    fun shortcutManager(): ShortcutManager? {
        return get(Context.SHORTCUT_SERVICE) as ShortcutManager?
    }

    fun storageManager(): StorageManager? {
        return get(Context.STORAGE_SERVICE) as StorageManager?
    }

    @RequiresApi(AndroidHelper.API_26)
    fun storageStatsManager(): StorageStatsManager? {
        return get(Context.STORAGE_STATS_SERVICE) as StorageStatsManager?
    }

    @RequiresApi(AndroidHelper.API_24)
    fun systemHealthService(): SystemHealthManager? {
        return get(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun telecomManager(): TelecomManager? {
        return get(Context.TELECOM_SERVICE) as TelecomManager?
    }

    fun telephonyManager(): TelephonyManager? {
        return get(Context.TELEPHONY_SERVICE) as TelephonyManager?
    }

    @RequiresApi(AndroidHelper.API_22)
    fun subscriptionManager(): SubscriptionManager? {
        return get(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager?
    }

    @RequiresApi(AndroidHelper.API_26)
    fun textClassificationManager(): TextClassificationManager? {
        return get(Context.TEXT_CLASSIFICATION_SERVICE) as TextClassificationManager?
    }

    fun textServicesManager(): TextServicesManager? {
        return get(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager?
    }

    @RequiresApi(AndroidHelper.API_21)
    fun tvInputManager(): TvInputManager? {
        return get(Context.TV_INPUT_SERVICE) as TvInputManager?
    }

    fun uiModeManager(): UiModeManager? {
        return get(Context.UI_MODE_SERVICE) as UiModeManager?
    }

    @RequiresApi(AndroidHelper.API_22)
    fun usageStatsManager(): UsageStatsManager? {
        return get(Context.USAGE_STATS_SERVICE) as UsageStatsManager?
    }

    fun usbManager(): UsbManager? {
        return get(Context.USB_SERVICE) as UsbManager?
    }

    @RequiresApi(AndroidHelper.API_17)
    fun userManager(): UserManager? {
        return get(Context.USER_SERVICE) as UserManager?
    }

    fun vibrator(): Vibrator? {
        return get(Context.VIBRATOR_SERVICE) as Vibrator?
    }

    fun wallpaperService(): WallpaperService? {
        return get(Context.WALLPAPER_SERVICE) as WallpaperService?
    }

    @RequiresApi(AndroidHelper.API_26)
    fun wifiAwareManager(): WifiAwareManager? {
        return get(Context.WIFI_AWARE_SERVICE) as WifiAwareManager?
    }

    fun wifiP2pManager(): WifiP2pManager? {
        return get(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }

    fun wifiManager(): WifiManager? {
        return get(Context.WIFI_SERVICE) as WifiManager?
    }

    fun windowManager(): WindowManager? {
        return get(Context.WINDOW_SERVICE) as WindowManager?
    }

}
