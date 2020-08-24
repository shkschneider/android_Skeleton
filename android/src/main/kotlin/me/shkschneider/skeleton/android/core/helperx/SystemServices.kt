package me.shkschneider.skeleton.android.core.helperx

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
import androidx.core.hardware.fingerprint.FingerprintManagerCompat // TODO
import me.shkschneider.skeleton.android.core.extensions.getSystemService
import me.shkschneider.skeleton.android.core.helper.AndroidHelper
import me.shkschneider.skeleton.android.core.helper.ContextHelper
import kotlin.reflect.KClass

// <http://developer.android.com/reference/android/content/Context.html>
object SystemServices {

    fun accessibilityManager() = get(AccessibilityManager::class)
    fun accountManager() = get(AccountManager::class)
    fun activityManager() = get(ActivityManager::class)
    fun alarmManager() = get(AlarmManager::class)
    fun appWidgetManager() = get(AppWidgetManager::class)
    fun appOpsManager() = get(AppOpsManager::class)
    fun audioManager() = get(AudioManager::class)
    fun batteryManager() = get(BatteryManager::class)
    @RequiresApi(AndroidHelper.API_29)
    fun biometricManager() = get(BiometricManager::class)
    fun bluetoothManager() = get(BluetoothManager::class)
    fun cameraManager() = get(CameraManager::class)
    fun captioningManager() = get(CaptioningManager::class)
    fun carrierConfigManager() = get(CarrierConfigManager::class)
    fun clipboardManager() = get(ClipboardManager::class)
    @RequiresApi(AndroidHelper.API_26)
    fun companionDeviceManager() = get(CompanionDeviceManager::class)
    fun connectivityManager() = get(ConnectivityManager::class)
    fun consumerIrManager() = get(ConsumerIrManager::class)
    fun devicePolicyManager() = get(DevicePolicyManager::class)
    fun displayManager() = get(DisplayManager::class)
    fun downloadManager() = get(DownloadManager::class)
    fun dropBoxManager() = get(DropBoxManager::class)
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Android.", ReplaceWith("BiometricManager"))
    fun fingerprintManager(context: Context) = FingerprintManagerCompat.from(context)
    @RequiresApi(AndroidHelper.API_24)
    fun hardwarePropertiesService() = get(HardwarePropertiesManager::class)
    fun inputMethodManager() = get(InputMethodManager::class)
    fun inputManager() = get(InputManager::class)
    fun jobScheduler() = get(JobScheduler::class)
    fun keyguardManager() = get(KeyguardManager::class)
    fun launcherApps() = get(LauncherApps::class)
    fun layoutInflater() = get(LayoutInflater::class)
    fun locationManager() = get(LocationManager::class)
    fun mediaProjectionManager() = get(MediaProjectionManager::class)
    fun mediaRouter() = get(MediaRouter::class)
    fun mediaSessionManager() = get(MediaSessionManager::class)
    fun midiManager() = get(MidiManager::class)
    fun networkStatsManager() = get(NetworkStatsManager::class)
    fun nfcManager() = get(NfcManager::class)
    fun notificationManager() = get(NotificationManager::class)
    fun nsdManager() = get(NsdManager::class)
    fun powerManager() = get(PowerManager::class)
    fun printManager() = get(PrintManager::class)
    fun restrictionsManager() = get(RestrictionsManager::class)
    @RequiresApi(AndroidHelper.API_29)
    fun roleManager() = get(RoleManager::class)
    fun searchManager() = get(SearchManager::class)
    fun sensorManager() = get(SensorManager::class)
    @RequiresApi(AndroidHelper.API_25)
    fun shortcutManager() = get(ShortcutManager::class)
    fun storageManager() = get(StorageManager::class)
    @RequiresApi(AndroidHelper.API_26)
    fun storageStatsManager() = get(StorageStatsManager::class)
    @RequiresApi(AndroidHelper.API_24)
    fun systemHealthService() = get(SystemHealthManager::class)
    fun telecomManager() = get(TelecomManager::class)
    fun telephonyManager() = get(TelephonyManager::class)
    fun subscriptionManager() = get(SubscriptionManager::class)
    @RequiresApi(AndroidHelper.API_26)
    fun textClassificationManager() = get(TextClassificationManager::class)
    fun textServicesManager() = get(TextServicesManager::class)
    fun tvInputManager() = get(TvInputManager::class)
    fun uiModeManager() = get(UiModeManager::class)
    fun usageStatsManager() = get(UsageStatsManager::class)
    fun usbManager() = get(UsbManager::class)
    fun userManager() = get(UserManager::class)
    fun vibrator() = get(Vibrator::class)
    fun wallpaperService() = get(WallpaperService::class)
    @RequiresApi(AndroidHelper.API_26)
    fun wifiAwareManager() = get(WifiAwareManager::class)
    fun wifiP2pManager(): WifiP2pManager? = get(WifiP2pManager::class)
    fun wifiManager() = get(WifiManager::class)
    fun windowManager() = get(WindowManager::class)

    private fun <T : Any> get(klass: KClass<T>) =
        ContextHelper.applicationContext().getSystemService(klass)

}
