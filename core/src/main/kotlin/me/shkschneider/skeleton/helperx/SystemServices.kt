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
import android.app.role.RoleManager
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
import android.hardware.biometrics.BiometricManager
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
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import me.shkschneider.skeleton.extensions.android.getSystemService
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ContextHelper
import kotlin.reflect.KClass

// <http://developer.android.com/reference/android/content/Context.html>
object SystemServices {

    private fun get(service: String): Any? =
            ContextHelper.applicationContext().getSystemService(service)


    @RequiresApi(AndroidHelper.API_23)
    private fun <T : Any> get(klass: KClass<T>): T? =
            ContextHelper.applicationContext().getSystemService(klass)

    fun accessibilityManager(): AccessibilityManager? =
            get(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?

    fun accountManager(): AccountManager? =
            get(Context.ACCOUNT_SERVICE) as AccountManager?


    fun activityManager(): ActivityManager? =
            get(Context.ACTIVITY_SERVICE) as ActivityManager?

    fun alarmManager(): AlarmManager? =
            get(Context.ALARM_SERVICE) as AlarmManager?

    @RequiresApi(AndroidHelper.API_21)
    fun appWidgetManager(): AppWidgetManager? =
            get(Context.APPWIDGET_SERVICE) as AppWidgetManager?

    @RequiresApi(AndroidHelper.API_19)
    fun appOpsManager(): AppOpsManager? =
            get(Context.APP_OPS_SERVICE) as AppOpsManager?

    fun audioManager(): AudioManager? =
            get(Context.AUDIO_SERVICE) as AudioManager?

    @RequiresApi(AndroidHelper.API_21)
    fun batteryManager(): BatteryManager? =
            get(Context.BATTERY_SERVICE) as BatteryManager?

    @RequiresApi(AndroidHelper.API_29)
    fun biometricManager(): BiometricManager? =
            get(Context.BIOMETRIC_SERVICE) as BiometricManager?

    @RequiresApi(AndroidHelper.API_18)
    fun bluetoothManager(): BluetoothManager? =
            get(Context.BLUETOOTH_SERVICE) as BluetoothManager?


    @RequiresApi(AndroidHelper.API_21)
    fun cameraManager(): CameraManager? =
            get(Context.CAMERA_SERVICE) as CameraManager?


    @RequiresApi(AndroidHelper.API_19)
    fun captioningManager(): CaptioningManager? =
            get(Context.CAPTIONING_SERVICE) as CaptioningManager?


    @RequiresApi(AndroidHelper.API_23)
    fun carrierConfigManager(): CarrierConfigManager? =
            get(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager?


    fun clipboardManager(): ClipboardManager? =
            get(Context.CLIPBOARD_SERVICE) as ClipboardManager?


    @RequiresApi(AndroidHelper.API_26)
    fun companionDeviceManager(): CompanionDeviceManager? =
            get(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager?


    fun connectivityManager(): ConnectivityManager? =
            get(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?


    @RequiresApi(AndroidHelper.API_19)
    fun consumerIrManager(): ConsumerIrManager? =
            get(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?


    fun devicePolicyManager(): DevicePolicyManager? =
            get(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?


    @RequiresApi(AndroidHelper.API_17)
    fun displayManager(): DisplayManager? =
            get(Context.DISPLAY_SERVICE) as DisplayManager?


    fun downloadManager(): DownloadManager? =
            get(Context.DOWNLOAD_SERVICE) as DownloadManager?


    fun dropBoxManager(): DropBoxManager? =
            get(Context.DROPBOX_SERVICE) as DropBoxManager?


    fun fingerprintManager(context: Context): FingerprintManagerCompat =
            FingerprintManagerCompat.from(context)


    @RequiresApi(AndroidHelper.API_24)
    fun hardwarePropertiesService(): HardwarePropertiesManager? =
            get(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager?


    fun inputMethodManager(): InputMethodManager? =
            get(Context.INPUT_METHOD_SERVICE) as InputMethodManager?


    @RequiresApi(AndroidHelper.API_16)
    fun inputManager(): InputManager? =
            get(Context.INPUT_SERVICE) as InputManager?


    @RequiresApi(AndroidHelper.API_21)
    fun jobScheduler(): JobScheduler? =
            get(Context.JOB_SCHEDULER_SERVICE) as JobScheduler?


    fun keyguardManager(): KeyguardManager? =
            get(Context.KEYGUARD_SERVICE) as KeyguardManager?


    @RequiresApi(AndroidHelper.API_21)
    fun launcherApps(): LauncherApps? =
            get(Context.LAUNCHER_APPS_SERVICE) as LauncherApps?


    fun layoutInflater(): LayoutInflater? =
            get(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?


    fun locationManager(): LocationManager? =
            get(Context.LOCATION_SERVICE) as LocationManager?


    @RequiresApi(AndroidHelper.API_21)
    fun mediaProjectionManager(): MediaProjectionManager? =
            get(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager?


    @RequiresApi(AndroidHelper.API_16)
    fun mediaRouter(): MediaRouter? =
            get(Context.MEDIA_ROUTER_SERVICE) as MediaRouter?


    @RequiresApi(AndroidHelper.API_21)
    fun mediaSessionManager(): MediaSessionManager? =
            get(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager?


    @RequiresApi(AndroidHelper.API_23)
    fun midiManager(): MidiManager? =
            get(Context.MIDI_SERVICE) as MidiManager?


    @RequiresApi(AndroidHelper.API_23)
    fun networkStatsManager(): NetworkStatsManager? =
            get(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager?


    fun nfcManager(): NfcManager? =
            get(Context.NFC_SERVICE) as NfcManager?


    fun notificationManager(): NotificationManager? =
            get(Context.NOTIFICATION_SERVICE) as NotificationManager?


    @RequiresApi(AndroidHelper.API_16)
    fun nsdManager(): NsdManager? =
            get(Context.NSD_SERVICE) as NsdManager?


    fun powerManager(): PowerManager? =
            get(Context.POWER_SERVICE) as PowerManager?


    @RequiresApi(AndroidHelper.API_19)
    fun printManager(): PrintManager? =
            get(Context.PRINT_SERVICE) as PrintManager?


    @RequiresApi(AndroidHelper.API_21)
    fun restrictionsManager(): RestrictionsManager? =
            get(Context.RESTRICTIONS_SERVICE) as RestrictionsManager?


    @RequiresApi(AndroidHelper.API_29)
    fun roleManager(): RoleManager? =
            get(Context.ROLE_SERVICE) as RoleManager?


    fun searchManager(): SearchManager? =
            get(Context.SEARCH_SERVICE) as SearchManager?


    fun sensorManager(): SensorManager? =
            get(Context.SENSOR_SERVICE) as SensorManager?


    @RequiresApi(AndroidHelper.API_25)
    fun shortcutManager(): ShortcutManager? =
            get(Context.SHORTCUT_SERVICE) as ShortcutManager?


    fun storageManager(): StorageManager? =
            get(Context.STORAGE_SERVICE) as StorageManager?


    @RequiresApi(AndroidHelper.API_26)
    fun storageStatsManager(): StorageStatsManager? =
            get(Context.STORAGE_STATS_SERVICE) as StorageStatsManager?


    @RequiresApi(AndroidHelper.API_24)
    fun systemHealthService(): SystemHealthManager? =
            get(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager?


    @RequiresApi(AndroidHelper.API_21)
    fun telecomManager(): TelecomManager? =
            get(Context.TELECOM_SERVICE) as TelecomManager?


    fun telephonyManager(): TelephonyManager? =
            get(Context.TELEPHONY_SERVICE) as TelephonyManager?


    @RequiresApi(AndroidHelper.API_22)
    fun subscriptionManager(): SubscriptionManager? =
            get(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager?


    @RequiresApi(AndroidHelper.API_26)
    fun textClassificationManager(): TextClassificationManager? =
            get(Context.TEXT_CLASSIFICATION_SERVICE) as TextClassificationManager?


    fun textServicesManager(): TextServicesManager? =
            get(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager?


    @RequiresApi(AndroidHelper.API_21)
    fun tvInputManager(): TvInputManager? =
            get(Context.TV_INPUT_SERVICE) as TvInputManager?


    fun uiModeManager(): UiModeManager? =
            get(Context.UI_MODE_SERVICE) as UiModeManager?


    @RequiresApi(AndroidHelper.API_22)
    fun usageStatsManager(): UsageStatsManager? =
            get(Context.USAGE_STATS_SERVICE) as UsageStatsManager?


    fun usbManager(): UsbManager? =
            get(Context.USB_SERVICE) as UsbManager?


    @RequiresApi(AndroidHelper.API_17)
    fun userManager(): UserManager? =
            get(Context.USER_SERVICE) as UserManager?


    fun vibrator(): Vibrator? =
            get(Context.VIBRATOR_SERVICE) as Vibrator?


    fun wallpaperService(): WallpaperService? =
            get(Context.WALLPAPER_SERVICE) as WallpaperService?


    @RequiresApi(AndroidHelper.API_26)
    fun wifiAwareManager(): WifiAwareManager? =
            get(Context.WIFI_AWARE_SERVICE) as WifiAwareManager?


    fun wifiP2pManager(): WifiP2pManager? =
            get(Context.WIFI_P2P_SERVICE) as WifiP2pManager?


    fun wifiManager(): WifiManager? =
            get(Context.WIFI_SERVICE) as WifiManager?


    fun windowManager(): WindowManager? =
            get(Context.WINDOW_SERVICE) as WindowManager?


}
