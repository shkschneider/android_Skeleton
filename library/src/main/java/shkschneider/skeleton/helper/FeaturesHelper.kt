package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.content.pm.PackageManager

// <http://developer.android.com/reference/android/content/pm/PackageManager.html>
object FeaturesHelper {

    // Useless: only used to keep track of new features
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS = PackageManager.FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS
    @SuppressLint("InlinedApi") // API-18+
    val FEATURE_APP_WIDGETS = PackageManager.FEATURE_APP_WIDGETS
    val FEATURE_AUDIO_LOW_LATENCY = PackageManager.FEATURE_AUDIO_LOW_LATENCY
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_AUDIO_OUTPUT = PackageManager.FEATURE_AUDIO_OUTPUT
    @SuppressLint("InlinedApi") // API-23+
    val FEATURE_AUDIO_PRO = PackageManager.FEATURE_AUDIO_PRO
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_AUTOFILL = PackageManager.FEATURE_AUTOFILL
    @SuppressLint("InlinedApi") // API-23+
    val FEATURE_AUTOMOTIVE = PackageManager.FEATURE_AUTOMOTIVE
    @SuppressLint("InlinedApi") // API-20+
    val FEATURE_BACKUP = PackageManager.FEATURE_BACKUP
    val FEATURE_BLUETOOTH = PackageManager.FEATURE_BLUETOOTH
    @SuppressLint("InlinedApi") // API-18+
    val FEATURE_BLUETOOTH_LE = PackageManager.FEATURE_BLUETOOTH_LE
    val FEATURE_CAMERA = PackageManager.FEATURE_CAMERA
    @SuppressLint("InlinedApi") // API-17+
    val FEATURE_CAMERA_ANY = PackageManager.FEATURE_CAMERA_ANY
    val FEATURE_CAMERA_AUTOFOCUS = PackageManager.FEATURE_CAMERA_AUTOFOCUS
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING = PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR = PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_CAMERA_CAPABILITY_RAW = PackageManager.FEATURE_CAMERA_CAPABILITY_RAW
    @SuppressLint("InlinedApi") // API-20+
    val FEATURE_CAMERA_EXTERNAL = PackageManager.FEATURE_CAMERA_EXTERNAL
    val FEATURE_CAMERA_FLASH = PackageManager.FEATURE_CAMERA_FLASH
    val FEATURE_CAMERA_FRONT = PackageManager.FEATURE_CAMERA_FRONT
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_CAMERA_LEVEL_FULL = PackageManager.FEATURE_CAMERA_LEVEL_FULL
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_COMPANION_DEVICE_SETUP = PackageManager.FEATURE_COMPANION_DEVICE_SETUP
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_CONNECTION_SERVICE = PackageManager.FEATURE_CONNECTION_SERVICE
    @SuppressLint("InlinedApi") // API-19+
    val FEATURE_CONSUMER_IR = PackageManager.FEATURE_CONSUMER_IR
    @SuppressLint("InlinedApi") // API-19+
    val FEATURE_DEVICE_ADMIN = PackageManager.FEATURE_DEVICE_ADMIN
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_EMBEDDED = PackageManager.FEATURE_EMBEDDED
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_ETHERNET = PackageManager.FEATURE_ETHERNET
    val FEATURE_FAKETOUCH = PackageManager.FEATURE_FAKETOUCH
    val FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT
    val FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND
    @SuppressLint("InlinedApi") // API-23+
    val FEATURE_FINGERPRINT = PackageManager.FEATURE_FINGERPRINT
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_FREEFORM_WINDOW_MANAGEMENT = PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_GAMEPAD = PackageManager.FEATURE_GAMEPAD
    @SuppressLint("InlinedApi") // API-23+
    val FEATURE_HIFI_SENSORS = PackageManager.FEATURE_HIFI_SENSORS
    @SuppressLint("InlinedApi") // API-18+
    val FEATURE_HOME_SCREEN = PackageManager.FEATURE_HOME_SCREEN
    @SuppressLint("InlinedApi") // API-18+
    val FEATURE_INPUT_METHODS = PackageManager.FEATURE_INPUT_METHODS
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_LEANBACK = PackageManager.FEATURE_LEANBACK
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_LEANBACK_ONLY = PackageManager.FEATURE_LEANBACK_ONLY
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_LIVE_TV = PackageManager.FEATURE_LIVE_TV
    val FEATURE_LIVE_WALLPAPER = PackageManager.FEATURE_LIVE_WALLPAPER
    val FEATURE_LOCATION = PackageManager.FEATURE_LOCATION
    val FEATURE_LOCATION_GPS = PackageManager.FEATURE_LOCATION_GPS
    val FEATURE_LOCATION_NETWORK = PackageManager.FEATURE_LOCATION_NETWORK
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_MANAGED_USERS = PackageManager.FEATURE_MANAGED_USERS
    val FEATURE_MICROPHONE = PackageManager.FEATURE_MICROPHONE
    @SuppressLint("InlinedApi") // API-23+
    val FEATURE_MIDI = PackageManager.FEATURE_MIDI
    val FEATURE_NFC = PackageManager.FEATURE_NFC
    @SuppressLint("InlinedApi") // API-19+
    val FEATURE_NFC_HOST_CARD_EMULATION = PackageManager.FEATURE_NFC_HOST_CARD_EMULATION
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_NFC_HOST_CARD_EMULATION_NFCF = PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_OPENGLES_EXTENSION_PACK = PackageManager.FEATURE_OPENGLES_EXTENSION_PACK
    @SuppressLint("InlinedApi") // API-27+
    val FEATURE_PC = PackageManager.FEATURE_PC
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_PICTURE_IN_PICTURE = PackageManager.FEATURE_PICTURE_IN_PICTURE
    @SuppressLint("InlinedApi") // API-20+
    val FEATURE_PRINTING = PackageManager.FEATURE_PRINTING
    @SuppressLint("InlinedApi") // API-27+
    val FEATURE_RAM_LOW = PackageManager.FEATURE_RAM_LOW
    @SuppressLint("InlinedApi") // API-27+
    val FEATURE_RAM_NORMAL = PackageManager.FEATURE_RAM_NORMAL
    val FEATURE_SCREEN_LANDSCAPE = PackageManager.FEATURE_SCREEN_LANDSCAPE
    val FEATURE_SCREEN_PORTRAIT = PackageManager.FEATURE_SCREEN_PORTRAIT
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_SECURELY_REMOVES_USERS = PackageManager.FEATURE_SECURELY_REMOVES_USERS
    val FEATURE_SENSOR_ACCELEROMETER = PackageManager.FEATURE_SENSOR_ACCELEROMETER
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_SENSOR_AMBIENT_TEMPERATURE = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE
    val FEATURE_SENSOR_BAROMETER = PackageManager.FEATURE_SENSOR_BAROMETER
    val FEATURE_SENSOR_COMPASS = PackageManager.FEATURE_SENSOR_COMPASS
    val FEATURE_SENSOR_GYROSCOPE = PackageManager.FEATURE_SENSOR_GYROSCOPE
    @SuppressLint("InlinedApi") // API-20+
    val FEATURE_SENSOR_HEART_RATE = PackageManager.FEATURE_SENSOR_HEART_RATE
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_SENSOR_HEART_RATE_ECG = PackageManager.FEATURE_SENSOR_HEART_RATE_ECG
    val FEATURE_SENSOR_LIGHT = PackageManager.FEATURE_SENSOR_LIGHT
    val FEATURE_SENSOR_PROXIMITY = PackageManager.FEATURE_SENSOR_PROXIMITY
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_SENSOR_RELATIVE_HUMIDITY = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY
    @SuppressLint("InlinedApi") // API-19+
    val FEATURE_SENSOR_STEP_COUNTER = PackageManager.FEATURE_SENSOR_STEP_COUNTER
    @SuppressLint("InlinedApi") // API-19+
    val FEATURE_SENSOR_STEP_DETECTOR = PackageManager.FEATURE_SENSOR_STEP_DETECTOR
    val FEATURE_SIP = PackageManager.FEATURE_SIP
    val FEATURE_SIP_VOIP = PackageManager.FEATURE_SIP_VOIP
    val FEATURE_TELEPHONY = PackageManager.FEATURE_TELEPHONY
    val FEATURE_TELEPHONY_CDMA = PackageManager.FEATURE_TELEPHONY_CDMA
    val FEATURE_TELEPHONY_GSM = PackageManager.FEATURE_TELEPHONY_GSM
    // FEATURE_TELEVISION
    val FEATURE_TOUCHSCREEN = PackageManager.FEATURE_TOUCHSCREEN
    val FEATURE_TOUCHSCREEN_MULTITOUCH = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH
    val FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT
    val FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND
    val FEATURE_USB_ACCESSORY = PackageManager.FEATURE_USB_ACCESSORY
    val FEATURE_USB_HOST = PackageManager.FEATURE_USB_HOST
    @SuppressLint("InlinedApi") // API-21+
    val FEATURE_VERIFIED_BOOT = PackageManager.FEATURE_VERIFIED_BOOT
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_VR_HEADTRACKING = PackageManager.FEATURE_VR_HEADTRACKING
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_VR_MODE = PackageManager.FEATURE_VR_MODE
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_VR_MODE_HIGH_PERFORMANCE = PackageManager.FEATURE_VR_MODE_HIGH_PERFORMANCE
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_VULKAN_HARDWARE_COMPUTE = PackageManager.FEATURE_VULKAN_HARDWARE_COMPUTE
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_VULKAN_HARDWARE_LEVEL = PackageManager.FEATURE_VULKAN_HARDWARE_LEVEL
    @SuppressLint("InlinedApi") // API-24+
    val FEATURE_VULKAN_HARDWARE_VERSION = PackageManager.FEATURE_VULKAN_HARDWARE_VERSION
    @SuppressLint("InlinedApi") // API-20+
    val FEATURE_WATCH = PackageManager.FEATURE_WATCH
    @SuppressLint("InlinedApi") // API-20+
    val FEATURE_WEBVIEW = PackageManager.FEATURE_WEBVIEW
    val FEATURE_WIFI = PackageManager.FEATURE_WIFI
    @SuppressLint("InlinedApi") // API-26+
    val FEATURE_WIFI_AWARE = PackageManager.FEATURE_WIFI_AWARE
    val FEATURE_WIFI_DIRECT = PackageManager.FEATURE_WIFI_DIRECT
    @SuppressLint("InlinedApi") // API-27+
    val FEATURE_WIFI_PASSPOINT = PackageManager.FEATURE_WIFI_PASSPOINT

    fun has(feature: String): Boolean {
        return ApplicationHelper.packageManager().hasSystemFeature(feature)
    }

}
