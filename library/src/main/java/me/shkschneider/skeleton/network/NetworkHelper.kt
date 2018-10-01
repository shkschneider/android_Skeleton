package me.shkschneider.skeleton.network

import android.Manifest
import android.net.wifi.WifiManager
import android.os.Build
import android.security.NetworkSecurityPolicy
import android.util.Patterns
import android.webkit.WebSettings
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.helper.SystemProperties
import me.shkschneider.skeleton.helper.SystemServices
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

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
                    if (! inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
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
                    if (! inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
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

}
