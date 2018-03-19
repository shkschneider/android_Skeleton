package me.shkschneider.skeleton.network

import android.Manifest
import android.annotation.SuppressLint
import android.net.wifi.WifiManager
import android.os.Build
import android.support.annotation.RequiresPermission
import android.util.Patterns
import android.webkit.WebSettings
import me.shkschneider.skeleton.helper.*
import me.shkschneider.skeleton.java.ReflectHelper
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object NetworkHelper {

    // <https://stackoverflow.com/a/39149126>
    @Deprecated("Does NOT work on API-26+")
    @SuppressLint("PrivateApi")
    fun hostname(): String? {
        try {
            val cls = Class.forName("android.os.SystemProperties")
            if (cls != null) {
                @Suppress("DEPRECATION")
                return ReflectHelper.Method.method(cls, "get", arrayOf(String::class.java), "net.hostname") as String?
            }
        }
        catch (e: ClassNotFoundException) {
            Logger.wtf(e)
        }
        // Most probably null
        return SystemProperties.get("net.hostname")
    }

    fun userAgent(): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return WebSettings.getDefaultUserAgent(ContextHelper.applicationContext())
        } else {
            return SystemProperties.get("http.agent")
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun connectedOrConnecting(): Boolean {
        val connectivityManager = SystemServices.connectivityManager()
        val networkInfo = connectivityManager?.activeNetworkInfo
        if (networkInfo == null) {
            Logger.warning("NetworkInfo was NULL")
            return false
        }
        return networkInfo.isConnectedOrConnecting
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    fun wifi(): Boolean {
        val wifiManager = SystemServices.wifiManager()
        if (wifiManager == null) {
            Logger.warning("wifiManager was NULL")
            return false
        }
        return wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED
    }

    fun ipAddresses(): List<String>? {
        try {
            val ipAddresses = ArrayList<String>()
            val enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces()
            while (enumerationNetworkInterface.hasMoreElements()) {
                val networkInterface = enumerationNetworkInterface.nextElement()
                val enumerationInetAddress = networkInterface.inetAddresses
                while (enumerationInetAddress.hasMoreElements()) {
                    val inetAddress = enumerationInetAddress.nextElement()
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
            val enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces()
            while (enumerationNetworkInterface.hasMoreElements()) {
                val networkInterface = enumerationNetworkInterface.nextElement()
                val enumerationInetAddress = networkInterface.inetAddresses
                while (enumerationInetAddress.hasMoreElements()) {
                    val inetAddress = enumerationInetAddress.nextElement()
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
