package me.shkschneider.skeleton.helper

import android.content.pm.PackageManager
import androidx.annotation.RequiresApi

// <http://developer.android.com/reference/android/content/pm/PackageManager.html>
object FeaturesHelper {

    // Useless: only used to keep track of new features
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS = PackageManager.FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS
    @RequiresApi(AndroidHelper.API_18)
    const val FEATURE_APP_WIDGETS = PackageManager.FEATURE_APP_WIDGETS
    const val FEATURE_AUDIO_LOW_LATENCY = PackageManager.FEATURE_AUDIO_LOW_LATENCY
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_AUDIO_OUTPUT = PackageManager.FEATURE_AUDIO_OUTPUT
    @RequiresApi(AndroidHelper.API_23)
    const val FEATURE_AUDIO_PRO = PackageManager.FEATURE_AUDIO_PRO
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_AUTOFILL = PackageManager.FEATURE_AUTOFILL
    @RequiresApi(AndroidHelper.API_23)
    const val FEATURE_AUTOMOTIVE = PackageManager.FEATURE_AUTOMOTIVE
    @RequiresApi(AndroidHelper.API_20)
    const val FEATURE_BACKUP = PackageManager.FEATURE_BACKUP
    const val FEATURE_BLUETOOTH = PackageManager.FEATURE_BLUETOOTH
    @RequiresApi(AndroidHelper.API_18)
    const val FEATURE_BLUETOOTH_LE = PackageManager.FEATURE_BLUETOOTH_LE
    const val FEATURE_CAMERA = PackageManager.FEATURE_CAMERA
    @RequiresApi(AndroidHelper.API_17)
    const val FEATURE_CAMERA_ANY = PackageManager.FEATURE_CAMERA_ANY
    @RequiresApi(AndroidHelper.API_28)
    const val FEATURE_CAMERA_AR = PackageManager.FEATURE_CAMERA_AR
    const val FEATURE_CAMERA_AUTOFOCUS = PackageManager.FEATURE_CAMERA_AUTOFOCUS
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING = PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR = PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_CAMERA_CAPABILITY_RAW = PackageManager.FEATURE_CAMERA_CAPABILITY_RAW
    @RequiresApi(AndroidHelper.API_20)
    const val FEATURE_CAMERA_EXTERNAL = PackageManager.FEATURE_CAMERA_EXTERNAL
    const val FEATURE_CAMERA_FLASH = PackageManager.FEATURE_CAMERA_FLASH
    const val FEATURE_CAMERA_FRONT = PackageManager.FEATURE_CAMERA_FRONT
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_CAMERA_LEVEL_FULL = PackageManager.FEATURE_CAMERA_LEVEL_FULL
    @RequiresApi(AndroidHelper.API_28)
    const val FEATURE_COMPANION_DEVICE_SETUP = PackageManager.FEATURE_COMPANION_DEVICE_SETUP
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_CONNECTION_SERVICE = PackageManager.FEATURE_CONNECTION_SERVICE
    @RequiresApi(AndroidHelper.API_19)
    const val FEATURE_CONSUMER_IR = PackageManager.FEATURE_CONSUMER_IR
    @RequiresApi(AndroidHelper.API_19)
    const val FEATURE_DEVICE_ADMIN = PackageManager.FEATURE_DEVICE_ADMIN
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_EMBEDDED = PackageManager.FEATURE_EMBEDDED
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_ETHERNET = PackageManager.FEATURE_ETHERNET
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_FACE = PackageManager.FEATURE_FACE
    const val FEATURE_FAKETOUCH = PackageManager.FEATURE_FAKETOUCH
    const val FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT
    const val FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND
    @RequiresApi(AndroidHelper.API_23)
    const val FEATURE_FINGERPRINT = PackageManager.FEATURE_FINGERPRINT
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_FREEFORM_WINDOW_MANAGEMENT = PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_GAMEPAD = PackageManager.FEATURE_GAMEPAD
    @RequiresApi(AndroidHelper.API_23)
    const val FEATURE_HIFI_SENSORS = PackageManager.FEATURE_HIFI_SENSORS
    @RequiresApi(AndroidHelper.API_18)
    const val FEATURE_HOME_SCREEN = PackageManager.FEATURE_HOME_SCREEN
    @RequiresApi(AndroidHelper.API_18)
    const val FEATURE_INPUT_METHODS = PackageManager.FEATURE_INPUT_METHODS
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_IPSEC_TUNNELS = PackageManager.FEATURE_IPSEC_TUNNELS
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_IRIS = PackageManager.FEATURE_IRIS
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_LEANBACK = PackageManager.FEATURE_LEANBACK
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_LEANBACK_ONLY = PackageManager.FEATURE_LEANBACK_ONLY
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_LIVE_TV = PackageManager.FEATURE_LIVE_TV
    const val FEATURE_LIVE_WALLPAPER = PackageManager.FEATURE_LIVE_WALLPAPER
    const val FEATURE_LOCATION = PackageManager.FEATURE_LOCATION
    const val FEATURE_LOCATION_GPS = PackageManager.FEATURE_LOCATION_GPS
    const val FEATURE_LOCATION_NETWORK = PackageManager.FEATURE_LOCATION_NETWORK
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_MANAGED_USERS = PackageManager.FEATURE_MANAGED_USERS
    const val FEATURE_MICROPHONE = PackageManager.FEATURE_MICROPHONE
    @RequiresApi(AndroidHelper.API_23)
    const val FEATURE_MIDI = PackageManager.FEATURE_MIDI
    const val FEATURE_NFC = PackageManager.FEATURE_NFC
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_NFC_BEAM = PackageManager.FEATURE_NFC_BEAM
    @RequiresApi(AndroidHelper.API_19)
    const val FEATURE_NFC_HOST_CARD_EMULATION = PackageManager.FEATURE_NFC_HOST_CARD_EMULATION
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_NFC_HOST_CARD_EMULATION_NFCF = PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE = PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC = PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_OPENGLES_EXTENSION_PACK = PackageManager.FEATURE_OPENGLES_EXTENSION_PACK
    @RequiresApi(AndroidHelper.API_27)
    const val FEATURE_PC = PackageManager.FEATURE_PC
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_PICTURE_IN_PICTURE = PackageManager.FEATURE_PICTURE_IN_PICTURE
    @RequiresApi(AndroidHelper.API_20)
    const val FEATURE_PRINTING = PackageManager.FEATURE_PRINTING
    @RequiresApi(AndroidHelper.API_27)
    const val FEATURE_RAM_LOW = PackageManager.FEATURE_RAM_LOW
    @RequiresApi(AndroidHelper.API_27)
    const val FEATURE_RAM_NORMAL = PackageManager.FEATURE_RAM_NORMAL
    const val FEATURE_SCREEN_LANDSCAPE = PackageManager.FEATURE_SCREEN_LANDSCAPE
    const val FEATURE_SCREEN_PORTRAIT = PackageManager.FEATURE_SCREEN_PORTRAIT
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_SECURELY_REMOVES_USERS = PackageManager.FEATURE_SECURELY_REMOVES_USERS
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_SECURE_LOCK_SCREEN = PackageManager.FEATURE_SECURE_LOCK_SCREEN
    const val FEATURE_SENSOR_ACCELEROMETER = PackageManager.FEATURE_SENSOR_ACCELEROMETER
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_SENSOR_AMBIENT_TEMPERATURE = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE
    const val FEATURE_SENSOR_BAROMETER = PackageManager.FEATURE_SENSOR_BAROMETER
    const val FEATURE_SENSOR_COMPASS = PackageManager.FEATURE_SENSOR_COMPASS
    const val FEATURE_SENSOR_GYROSCOPE = PackageManager.FEATURE_SENSOR_GYROSCOPE
    @RequiresApi(AndroidHelper.API_20)
    const val FEATURE_SENSOR_HEART_RATE = PackageManager.FEATURE_SENSOR_HEART_RATE
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_SENSOR_HEART_RATE_ECG = PackageManager.FEATURE_SENSOR_HEART_RATE_ECG
    const val FEATURE_SENSOR_LIGHT = PackageManager.FEATURE_SENSOR_LIGHT
    const val FEATURE_SENSOR_PROXIMITY = PackageManager.FEATURE_SENSOR_PROXIMITY
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_SENSOR_RELATIVE_HUMIDITY = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY
    @RequiresApi(AndroidHelper.API_19)
    const val FEATURE_SENSOR_STEP_COUNTER = PackageManager.FEATURE_SENSOR_STEP_COUNTER
    @RequiresApi(AndroidHelper.API_19)
    const val FEATURE_SENSOR_STEP_DETECTOR = PackageManager.FEATURE_SENSOR_STEP_DETECTOR
    const val FEATURE_SIP = PackageManager.FEATURE_SIP
    const val FEATURE_SIP_VOIP = PackageManager.FEATURE_SIP_VOIP
    @RequiresApi(AndroidHelper.API_28)
    const val FEATURE_STRONGBOX_KEYSTORE = PackageManager.FEATURE_STRONGBOX_KEYSTORE
    const val FEATURE_TELEPHONY = PackageManager.FEATURE_TELEPHONY
    const val FEATURE_TELEPHONY_CDMA = PackageManager.FEATURE_TELEPHONY_CDMA
    @RequiresApi(AndroidHelper.API_28)
    const val FEATURE_TELEPHONY_EUICC = PackageManager.FEATURE_TELEPHONY_EUICC
    const val FEATURE_TELEPHONY_GSM = PackageManager.FEATURE_TELEPHONY_GSM
    @RequiresApi(AndroidHelper.API_29)
    const val FEATURE_TELEPHONY_IMS = PackageManager.FEATURE_TELEPHONY_IMS
    @RequiresApi(AndroidHelper.API_28)
    const val FEATURE_TELEPHONY_MBMS = PackageManager.FEATURE_TELEPHONY_MBMS
    @Suppress("DEPRECATION")
    @Deprecated("Use FEATURE_LEANBACK instead.")
    const val FEATURE_TELEVISION = PackageManager.FEATURE_TELEVISION
    const val FEATURE_TOUCHSCREEN = PackageManager.FEATURE_TOUCHSCREEN
    const val FEATURE_TOUCHSCREEN_MULTITOUCH = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH
    const val FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT
    const val FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND
    const val FEATURE_USB_ACCESSORY = PackageManager.FEATURE_USB_ACCESSORY
    const val FEATURE_USB_HOST = PackageManager.FEATURE_USB_HOST
    @RequiresApi(AndroidHelper.API_21)
    const val FEATURE_VERIFIED_BOOT = PackageManager.FEATURE_VERIFIED_BOOT
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_VR_HEADTRACKING = PackageManager.FEATURE_VR_HEADTRACKING
    @Suppress("DEPRECATION")
    @Deprecated("Use FEATURE_VR_MODE_HIGH_PERFORMANCE instead.")
    const val FEATURE_VR_MODE = PackageManager.FEATURE_VR_MODE
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_VR_MODE_HIGH_PERFORMANCE = PackageManager.FEATURE_VR_MODE_HIGH_PERFORMANCE
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_VULKAN_HARDWARE_COMPUTE = PackageManager.FEATURE_VULKAN_HARDWARE_COMPUTE
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_VULKAN_HARDWARE_LEVEL = PackageManager.FEATURE_VULKAN_HARDWARE_LEVEL
    @RequiresApi(AndroidHelper.API_24)
    const val FEATURE_VULKAN_HARDWARE_VERSION = PackageManager.FEATURE_VULKAN_HARDWARE_VERSION
    @RequiresApi(AndroidHelper.API_20)
    const val FEATURE_WATCH = PackageManager.FEATURE_WATCH
    @RequiresApi(AndroidHelper.API_20)
    const val FEATURE_WEBVIEW = PackageManager.FEATURE_WEBVIEW
    const val FEATURE_WIFI = PackageManager.FEATURE_WIFI
    @RequiresApi(AndroidHelper.API_26)
    const val FEATURE_WIFI_AWARE = PackageManager.FEATURE_WIFI_AWARE
    const val FEATURE_WIFI_DIRECT = PackageManager.FEATURE_WIFI_DIRECT
    @RequiresApi(AndroidHelper.API_27)
    const val FEATURE_WIFI_PASSPOINT = PackageManager.FEATURE_WIFI_PASSPOINT
    @RequiresApi(AndroidHelper.API_28)
    const val FEATURE_WIFI_RTT = PackageManager.FEATURE_WIFI_RTT

    fun has(feature: String): Boolean {
        return ApplicationHelper.packageManager.hasSystemFeature(feature)
    }

}
