package me.shkschneider.skeleton.network

import android.Manifest
import android.annotation.TargetApi
import android.net.wifi.WifiManager
import android.support.annotation.RequiresPermission
import android.webkit.WebSettings

import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.ArrayList

import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.helper.SystemProperties
import me.shkschneider.skeleton.helper.SystemServices
import me.shkschneider.skeleton.java.ClassHelper
import me.shkschneider.skeleton.java.ReflectHelper

object NetworkHelper {

    // <https://stackoverflow.com/a/39149126>
    @Deprecated("Does NOT work on API-26+")
    fun hostname(): String? {
        val cls = ClassHelper.get("android.os.SystemProperties")
        return if (cls != null) {
            ReflectHelper.Method.method(cls, "get", arrayOf(String::class.java), "net.hostname") as String?
        } else SystemProperties.get("net.hostname")
        // Most probably null
    }

    fun userAgent(): String? {
        return if (AndroidHelper.api() >= AndroidHelper.API_17) {
            userAgent17()
        } else SystemProperties.get("http.agent")
    }

    // <https://stackoverflow.com/a/43238397>
    @TargetApi(AndroidHelper.API_17)
    private fun userAgent17(): String {
        return WebSettings.getDefaultUserAgent(ContextHelper.applicationContext())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun connectedOrConnecting(): Boolean {
        val connectivityManager = SystemServices.connectivityManager()
        val networkInfo = connectivityManager?.activeNetworkInfo
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL")
            return false
        }
        return networkInfo.isConnectedOrConnecting
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    fun wifi(): Boolean {
        val wifiManager = SystemServices.wifiManager()
        if (wifiManager == null) {
            LogHelper.warning("wifiManager was NULL")
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
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        ipAddresses.add(ipAddress)
                    }
                }
            }
            return ipAddresses
        } catch (e: SocketException) {
            LogHelper.wtf(e)
            return null
        }

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
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return ipAddress
                    }
                }
            }
            return null
        } catch (e: SocketException) {
            LogHelper.wtf(e)
            return null
        }

    }

}
