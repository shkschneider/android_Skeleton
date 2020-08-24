package me.shkschneider.skeleton.android.core.helper

import android.content.pm.PackageManager
import androidx.annotation.RequiresApi

// <http://developer.android.com/reference/android/content/pm/PackageManager.html>
sealed class Features(val name: String) {

    // Useless: only used to keep track of new features
    @RequiresApi(AndroidHelper.API_26)
    object ActivitiesOnSecondaryDisplays : Features(PackageManager.FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS)
    object AppWidgets : Features(PackageManager.FEATURE_APP_WIDGETS)
    object AudioLowLatency : Features(PackageManager.FEATURE_AUDIO_LOW_LATENCY)
    object AudioOutput : Features(PackageManager.FEATURE_AUDIO_OUTPUT)
    object AudioPro : Features(PackageManager.FEATURE_AUDIO_PRO)
    @RequiresApi(AndroidHelper.API_26)
    object Autofill : Features(PackageManager.FEATURE_AUTOFILL)
    object Automotive : Features(PackageManager.FEATURE_AUTOMOTIVE)
    object Backup : Features(PackageManager.FEATURE_BACKUP)
    object Bluetooth : Features(PackageManager.FEATURE_BLUETOOTH)
    object BluetoothLe : Features(PackageManager.FEATURE_BLUETOOTH_LE)
    object Camera : Features(PackageManager.FEATURE_CAMERA)
    object CameraAny : Features(PackageManager.FEATURE_CAMERA_ANY)
    @RequiresApi(AndroidHelper.API_28)
    object CameraAr : Features(PackageManager.FEATURE_CAMERA_AR)
    object CameraAutofocus : Features(PackageManager.FEATURE_CAMERA_AUTOFOCUS)
    object CameraCapibilityManualPostProcessing : Features(PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING)
    object CameraCapicilityManualSensor : Features(PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR)
    object CameraCapabilityRaw : Features(PackageManager.FEATURE_CAMERA_CAPABILITY_RAW)
    object CameraExternal : Features(PackageManager.FEATURE_CAMERA_EXTERNAL)
    object CameraFlash : Features(PackageManager.FEATURE_CAMERA_FLASH)
    object CameraFront : Features(PackageManager.FEATURE_CAMERA_FRONT)
    object CameraLevelFull : Features(PackageManager.FEATURE_CAMERA_LEVEL_FULL)
    @RequiresApi(AndroidHelper.API_28)
    object CompanionDeviceSetup : Features(PackageManager.FEATURE_COMPANION_DEVICE_SETUP)
    object ConnectionService : Features(PackageManager.FEATURE_CONNECTION_SERVICE)
    object ConsumerIr : Features(PackageManager.FEATURE_CONSUMER_IR)
    object DeviceAdmin : Features(PackageManager.FEATURE_DEVICE_ADMIN)
    @RequiresApi(AndroidHelper.API_26)
    object Embedded : Features(PackageManager.FEATURE_EMBEDDED)
    @RequiresApi(AndroidHelper.API_24)
    object Ethermet : Features(PackageManager.FEATURE_ETHERNET)
    @RequiresApi(AndroidHelper.API_29)
    object Face : Features(PackageManager.FEATURE_FACE)
    object Faketouch : Features(PackageManager.FEATURE_FAKETOUCH)
    object FaketouchMultitouchDistinct : Features(PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT)
    object FaketouchMultitouchJazzhand : Features(PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND)
    object Fingerprint : Features(PackageManager.FEATURE_FINGERPRINT)
    @RequiresApi(AndroidHelper.API_24)
    object FreeformWindowManagement : Features(PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT)
    object Gamepad : Features(PackageManager.FEATURE_GAMEPAD)
    object HifiSensors : Features(PackageManager.FEATURE_HIFI_SENSORS)
    object HomeScreen : Features(PackageManager.FEATURE_HOME_SCREEN)
    object InputMethods : Features(PackageManager.FEATURE_INPUT_METHODS)
    @RequiresApi(AndroidHelper.API_29)
    object IpsecTunnels : Features(PackageManager.FEATURE_IPSEC_TUNNELS)
    @RequiresApi(AndroidHelper.API_29)
    object Iris : Features(PackageManager.FEATURE_IRIS)
    object Leanback : Features(PackageManager.FEATURE_LEANBACK)
    @RequiresApi(AndroidHelper.API_26)
    object LeanbackOnly : Features(PackageManager.FEATURE_LEANBACK_ONLY)
    object LiveTv : Features(PackageManager.FEATURE_LIVE_TV)
    object LiveWallpaper : Features(PackageManager.FEATURE_LIVE_WALLPAPER)
    object Location : Features(PackageManager.FEATURE_LOCATION)
    object LocationGps : Features(PackageManager.FEATURE_LOCATION_GPS)
    object LocationNetwork : Features(PackageManager.FEATURE_LOCATION_NETWORK)
    object ManagedUsers : Features(PackageManager.FEATURE_MANAGED_USERS)
    object Microphone : Features(PackageManager.FEATURE_MICROPHONE)
    object Midi : Features(PackageManager.FEATURE_MIDI)
    object Nfc : Features(PackageManager.FEATURE_NFC)
    @RequiresApi(AndroidHelper.API_29)
    object NfcBeam : Features(PackageManager.FEATURE_NFC_BEAM)
    object NfcHostCardEmulation : Features(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)
    @RequiresApi(AndroidHelper.API_24)
    object NfcHostCardEmulationNfcf : Features(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF)
    @RequiresApi(AndroidHelper.API_29)
    object NfcHostCardEmulationEse : Features(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE)
    @RequiresApi(AndroidHelper.API_29)
    object NfsOffHostCardEmulationUicc : Features(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC)
    object OpenglesExtensionPack : Features(PackageManager.FEATURE_OPENGLES_EXTENSION_PACK)
    @RequiresApi(AndroidHelper.API_27)
    object Pc : Features(PackageManager.FEATURE_PC)
    @RequiresApi(AndroidHelper.API_24)
    object PictureInPicture : Features(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    object Printing : Features(PackageManager.FEATURE_PRINTING)
    @RequiresApi(AndroidHelper.API_27)
    object RawLow : Features(PackageManager.FEATURE_RAM_LOW)
    @RequiresApi(AndroidHelper.API_27)
    object RamNormal : Features(PackageManager.FEATURE_RAM_NORMAL)
    object ScreenLandscape : Features(PackageManager.FEATURE_SCREEN_LANDSCAPE)
    object ScreenPortrait : Features(PackageManager.FEATURE_SCREEN_PORTRAIT)
    object SecurelyRemovesUsers : Features(PackageManager.FEATURE_SECURELY_REMOVES_USERS)
    @RequiresApi(AndroidHelper.API_29)
    object SecureLockScreen : Features(PackageManager.FEATURE_SECURE_LOCK_SCREEN)
    object SensorAccelerometer : Features(PackageManager.FEATURE_SENSOR_ACCELEROMETER)
    object SensorAmbientTemperature : Features(PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE)
    object SensorBarometer : Features(PackageManager.FEATURE_SENSOR_BAROMETER)
    object SensorCompass : Features(PackageManager.FEATURE_SENSOR_COMPASS)
    object SensorGyroscope : Features(PackageManager.FEATURE_SENSOR_GYROSCOPE)
    object SensorHeartRate : Features(PackageManager.FEATURE_SENSOR_HEART_RATE)
    object SensorHeartRateEcg : Features(PackageManager.FEATURE_SENSOR_HEART_RATE_ECG)
    object SensorLight : Features(PackageManager.FEATURE_SENSOR_LIGHT)
    object SensorProximity : Features(PackageManager.FEATURE_SENSOR_PROXIMITY)
    object SensorRelativeHumidity : Features(PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY)
    object SensorStepCounter : Features(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
    object SensorStepDetector : Features(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)
    object Sip : Features(PackageManager.FEATURE_SIP)
    object SipVoip : Features(PackageManager.FEATURE_SIP_VOIP)
    @RequiresApi(AndroidHelper.API_28)
    object StrongboxKeystore : Features(PackageManager.FEATURE_STRONGBOX_KEYSTORE)
    object Telephony : Features(PackageManager.FEATURE_TELEPHONY)
    object TelephonyCdma : Features(PackageManager.FEATURE_TELEPHONY_CDMA)
    @RequiresApi(AndroidHelper.API_28)
    object TelephonyEuicc : Features(PackageManager.FEATURE_TELEPHONY_EUICC)
    object TelephonyGsm : Features(PackageManager.FEATURE_TELEPHONY_GSM)
    @RequiresApi(AndroidHelper.API_29)
    object TelephonyIms : Features(PackageManager.FEATURE_TELEPHONY_IMS)
    @RequiresApi(AndroidHelper.API_28)
    object TelephonyMbms : Features(PackageManager.FEATURE_TELEPHONY_MBMS)
    @Suppress("DEPRECATION")
    @Deprecated("Use FEATURE_LEANBACK instead.")
    object Television : Features(PackageManager.FEATURE_TELEVISION)
    object Touchscreen : Features(PackageManager.FEATURE_TOUCHSCREEN)
    object TouchscreenMultitouch : Features(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)
    object TouchscreenMultitouchDistinct : Features(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)
    object TouchscreenMultitouchJazzhand : Features(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)
    object UsbAccessory : Features(PackageManager.FEATURE_USB_ACCESSORY)
    object UsbHost : Features(PackageManager.FEATURE_USB_HOST)
    object VerifiedBoot : Features(PackageManager.FEATURE_VERIFIED_BOOT)
    @RequiresApi(AndroidHelper.API_26)
    object VrHeadtracking : Features(PackageManager.FEATURE_VR_HEADTRACKING)
    @Suppress("DEPRECATION")
    @Deprecated("Use FEATURE_VR_MODE_HIGH_PERFORMANCE instead.")
    object VrMode : Features(PackageManager.FEATURE_VR_MODE)
    @RequiresApi(AndroidHelper.API_24)
    object BrModeHightPerformance : Features(PackageManager.FEATURE_VR_MODE_HIGH_PERFORMANCE)
    @RequiresApi(AndroidHelper.API_26)
    object VulkanHardwareCompute : Features(PackageManager.FEATURE_VULKAN_HARDWARE_COMPUTE)
    @RequiresApi(AndroidHelper.API_24)
    object VulkanHardwareLevel : Features(PackageManager.FEATURE_VULKAN_HARDWARE_LEVEL)
    @RequiresApi(AndroidHelper.API_24)
    object VulkanHardwareVersion : Features(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION)
    object Watch : Features(PackageManager.FEATURE_WATCH)
    object Webview : Features(PackageManager.FEATURE_WEBVIEW)
    object Wifi : Features(PackageManager.FEATURE_WIFI)
    @RequiresApi(AndroidHelper.API_26)
    object WifiAware : Features(PackageManager.FEATURE_WIFI_AWARE)
    object WifiDirect : Features(PackageManager.FEATURE_WIFI_DIRECT)
    @RequiresApi(AndroidHelper.API_27)
    object WifiPasspoint : Features(PackageManager.FEATURE_WIFI_PASSPOINT)
    @RequiresApi(AndroidHelper.API_28)
    object WifiRtt : Features(PackageManager.FEATURE_WIFI_RTT)

    val isAvailable: Boolean get() = ApplicationHelper.packageManager.hasSystemFeature(name)

    // TODO companion object { fun list() }

}
