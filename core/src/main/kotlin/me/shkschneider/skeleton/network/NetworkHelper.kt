package me.shkschneider.skeleton.network

import android.Manifest
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.security.NetworkSecurityPolicy
import android.telephony.TelephonyManager
import android.util.Patterns
import android.webkit.WebSettings
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.helperx.SystemProperties
import me.shkschneider.skeleton.helperx.SystemServices
import me.shkschneider.skeleton.networkx.HttpURLConnectionWebService
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException


object NetworkHelper {

    fun userAgent(): String? {
        if (Build.VERSION.SDK_INT >= 17) {
            return WebSettings.getDefaultUserAgent(ContextHelper.applicationContext())
        } else {
            return SystemProperties.get("http.agent")
        }
    }

    // Affects platform's components like DownloadManager or MediaPlayer...
    fun isCleartextTrafficPermitted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted
        } else {
            return true
        }
    }

    @Deprecated("Apps should instead use the ConnectivityManager.NetworkCallback API to learn about connectivity changes.")
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun connectedOrConnecting(): Boolean {
        val connectivityManager = SystemServices.connectivityManager()
        val networkInfo = connectivityManager?.activeNetworkInfo
        @Suppress("DEPRECATION")
        return networkInfo?.isConnectedOrConnecting ?: run {
            Logger.warning("NetworkInfo was NULL")
            return false
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    fun wifi(): Boolean {
        SystemServices.wifiManager()?.let { wifiManager ->
            return wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED
        } ?: run {
            Logger.warning("WifiManager was NULL")
            return false
        }
    }

    fun ipAddresses(): List<String>? {
        try {
            val ipAddresses = ArrayList<String>()
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val inetAddresses = networkInterfaces.nextElement().inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    val inetAddress = inetAddresses.nextElement()
                    val ipAddress = inetAddress.hostAddress
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        ipAddresses.add(ipAddress)
                    }
                }
            }
            return ipAddresses
        } catch (e: SocketException) {
            Logger.wtf(e)
            return null
        }

    }

    fun ipAddress(string: String): Boolean {
        return string.matches(Patterns.IP_ADDRESS.toRegex())
    }

    fun ipAddress(): String? {
        try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val inetAddresses = networkInterfaces.nextElement().inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    val inetAddress = inetAddresses.nextElement()
                    val ipAddress = inetAddress.hostAddress
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return ipAddress
                    }
                }
            }
            return null
        } catch (e: SocketException) {
            Logger.wtf(e)
            return null
        }
    }

    @Deprecated("Deprecated in Java.") // TODO
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun networkType(): NetworkType {
        return SystemServices.connectivityManager()?.let { connectivityManager ->
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET)?.state in
                    setOf(NetworkInfo.State.CONNECTED, NetworkInfo.State.CONNECTING)) {
                return NetworkType.ETHERNET
            }
            return SystemServices.connectivityManager()?.activeNetworkInfo?.let { networkInfo ->
                with(networkInfo) {
                    when {
                        networkInfo.type == ConnectivityManager.TYPE_WIFI -> return NetworkType.WIFI
                        networkInfo.type == ConnectivityManager.TYPE_MOBILE -> {
                            if (networkInfo.subtype in setOf(TelephonyManager.NETWORK_TYPE_GSM,
                                            TelephonyManager.NETWORK_TYPE_GPRS,
                                            TelephonyManager.NETWORK_TYPE_CDMA,
                                            TelephonyManager.NETWORK_TYPE_1xRTT,
                                            TelephonyManager.NETWORK_TYPE_IDEN)) {
                                return NetworkType.G2
                            }
                            else if (networkInfo.subtype in setOf(TelephonyManager.NETWORK_TYPE_TD_SCDMA,
                                            TelephonyManager.NETWORK_TYPE_EVDO_A,
                                            TelephonyManager.NETWORK_TYPE_UMTS,
                                            TelephonyManager.NETWORK_TYPE_EVDO_0,
                                            TelephonyManager.NETWORK_TYPE_HSDPA,
                                            TelephonyManager.NETWORK_TYPE_HSUPA,
                                            TelephonyManager.NETWORK_TYPE_HSPA,
                                            TelephonyManager.NETWORK_TYPE_EVDO_B,
                                            TelephonyManager.NETWORK_TYPE_EHRPD,
                                            TelephonyManager.NETWORK_TYPE_HSPAP)) {
                                return NetworkType.G3
                            }
                            else if (networkInfo.subtype in setOf(TelephonyManager.NETWORK_TYPE_IWLAN,
                                            TelephonyManager.NETWORK_TYPE_LTE)) {
                                return NetworkType.G4
                            }
                            // TODO NetworkType.G5
                            else {
                                return NetworkType.UNKNOWN
                            }
                        }
                        else -> {
                            return if (networkInfo.subtypeName.contains("CDMA")) NetworkType.G3 else NetworkType.UNKNOWN
                        }
                    }
                }
            } ?: NetworkHelper.NetworkType.NONE
        } ?: NetworkHelper.NetworkType.NONE
    }

    enum class NetworkType(val value: String) {
        ETHERNET("ETHERNET"),
        WIFI("WIFI"),
        // TODO G5,
        G4("4G"),
        G3("3G"),
        G2("2G"),
        UNKNOWN("UNKNOWN"),
        NONE("NONE");

        // <https://stackoverflow.com/a/55020163/603270>
        fun fromString(type: String): NetworkType? = NetworkType.values().find {
            it.value.toLowerCase() == type.toLowerCase()
        }
    }

}
